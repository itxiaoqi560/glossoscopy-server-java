package com.tcm.glossoscopy.mq;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.rabbitmq.client.*;
import com.tcm.glossoscopy.cache.RedisCache;
import com.tcm.glossoscopy.constant.Constant;
import com.tcm.glossoscopy.constant.RedisConstant;
import com.tcm.glossoscopy.utils.AliSmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class PhoneCodeMessageQueue implements Runnable {
    @Resource
    private ConnectionFactory connectionFactory;
    @Resource
    private RedisCache redisCache;
    @Resource
    private AliSmsUtil aliSmsUtil;
    private Connection connection;
    //交换机
    private static final String EXCHANGE_NAME = "PHONE_CODE_EXCHANGE";
    //队列
    private static final String QUEUE_NAME = "PHONE_CODE_QUEUE";
    //静态标志位，确保init只执行一次
    private static volatile boolean isInitialized = false;
    private static final Object initLock = new Object();
    //消费者宕机标志
    private CountDownLatch countDown;

    //消费消息
    public void consume(Channel channel) throws IOException {
        //接受消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                //获取手机号
                String phoneNumber = new String(message.getBody());
                //随机生成六位数字动态码
                String phoneCode = RandomUtil.randomNumbers(6);
                String key = RedisConstant.PHONE_CODE_PREFIX_KEY + phoneNumber;
                //redis缓存动态码并设置有效期五分钟
                redisCache.save(key, phoneCode,RedisConstant.PHONE_CODE_EXPIRE, TimeUnit.MINUTES);
                //发送动态码
                aliSmsUtil.sendMessage(phoneNumber, phoneCode);
                //手动确认
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                log.info("动态码队列成功消费消息：{}", phoneNumber);
            } catch (Exception e) {
                log.error("动态码队列处理消息失败：{}", e.getMessage());
                //检查连接是否已关闭
                while(ObjectUtil.isEmpty(connection) || !connection.isOpen()){
                    try {
                        connection = connectionFactory.newConnection();
                    } catch (TimeoutException ex) {
                        log.error("与rabbit重新建立连接失败");
                    }
                }
                // 否定确认消息，并重新放回队列
                if (Objects.isNull(channel) || !channel.isOpen()) {
                    Channel channel2 = connection.createChannel();
                    channel2.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
                } else {
                    channel.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
                }
            } finally {
                //消费者宕机
                countDown.countDown();
            }
        };
        //取消消息回调
        CancelCallback cancelCallback = consumerTag -> {
            log.error("动态码队列进程中断");
            //消费者宕机
            countDown.countDown();
        };
        /**
         * 消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答，ture：自动应答
         * 3.消费者未成功消费的回调
         * 4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
    }

    //生产消息
    public void produce(String message) {
        try (Channel channel = connection.createChannel()) {
            /**
             * 发送消息
             * 1.发送到哪个交换机
             * 2.队列名称
             * 3.其他参数信息
             * 4.发送消息的消息体
             */
            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes());
            log.info("成功将消息{}存放至动态码队列中", message);
        } catch (Exception e) {
            log.error("发送动态码失败");
        }
    }

    @PostConstruct
    private void init() throws IOException, TimeoutException {
        if (!isInitialized) {
            synchronized (initLock) {
                if (!isInitialized) {
                    connection = connectionFactory.newConnection();
                    //启用自动恢复，因网络波动等问题造成的连接异常关闭会尝试恢复
                    connectionFactory.setAutomaticRecoveryEnabled(true);
                    //设置恢复间隔，尝试恢复间隔时间为5秒
                    connectionFactory.setNetworkRecoveryInterval(5000);
                    try (Channel channel = connection.createChannel()) {
                        /**
                         * 声明和创建交换机
                         * 1.交换机的名称
                         * 2.交换机的类型：direct、topic或者fanout和headers， headers类型的交换器的性能很差，不建议使用。
                         * 3.指定交换机是否要持久化，如果设置为true，那么交换机的元数据要持久化到内存中
                         * 4.指定交换机在没有队列与其绑定时，是否删除，设置为false表示不删除；
                         * 5.Map<String, Object>类型，用来指定交换机其它一些结构化的参数，我在这里直接设置为null。
                         */
                        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, null);
                        /**
                         * 生成一个队列
                         * 1.队列的名称
                         * 2.队列是否要持久化，但是需要注意，这里的持久化只是队列名称等这些队列元数据的持久化，不是队列中消息的持久化
                         * 3.表示队列是不是私有的，如果是私有的，只有创建它的应用程序才能从队列消费消息；
                         * 4.队列在没有消费者订阅时是否自动删除
                         * 5.队列的一些结构化信息，比如声明死信队列、磁盘队列会用到。
                         */
                        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                        /**
                         * 将交换机和队列进行绑定
                         * 1.队列名称
                         * 2.交换机名称
                         * 3.路由键，在直连模式下为队列名称。
                         */
                        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, QUEUE_NAME);
                        //启动消费程序
                        new Thread(this).start();
                        //设置初始化标志位为true
                        isInitialized = true;
                        log.info("动态码队列初始化成功");
                    } catch (Exception e) {
                        log.error("动态码队列初始化失败：{}", e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try (Channel channel = connection.createChannel()) {
                //初始化消费者宕机标志
                countDown = new CountDownLatch(1);
                //消费消息
                consume(channel);
                //等待消费者宕机
                countDown.await();
                try {
                    //等待一段时间后重试
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.error("重试等待被中断：{}", e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            } catch (Exception e) {
                log.error("动态码消费失败，尝试重启动态码消费者");
            }
        }
    }
}
