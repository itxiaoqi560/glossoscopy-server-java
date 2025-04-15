//package com.tcm.glossoscopy.mq;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.remoting.exception.RemotingException;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Slf4j
//@Component
//public class RocketMessageQueue {
//    //定义消费者组名
//    private static final String CONSUMER_GROUP_NAME = "PHONE_CODE_CONSUMER_GROUP";
//    //定义生产者组名
//    private static final String PRODUCER_GROUP_NAME = "PHONE_CODE_PRODUCER_GROUP";
//    //设置NameServer地址
//    private static final String NAME_SRV_ADD = "localhost:9876";
//    private static final String COMMON_TOPIC = "COMMON";
//    private static final String PHONE_CODE_TAG = "PHONE_CODE";
//
//    //创建生产者实例
//    DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
//
//    {
//        //设置生产者的Nameserver地址
//        producer.setNamesrvAddr(NAME_SRV_ADD);
//        //启动生产者
//        try {
//            producer.start();
//        } catch (MQClientException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void produce(String message) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
//        //创建消息实例
//        Message msg = new Message(COMMON_TOPIC, PHONE_CODE_TAG, message.getBytes());
//        //发送消息
//        SendResult sendResult = producer.send(msg);
//        log.info("发送消息{}：{}",message,sendResult.getSendStatus());
//    }
//
//    @PostConstruct
//    public void init() throws MQClientException {
//        // 创建并启动四个消费者
//        for (int i = 1; i <= 4; i++) {
//            //创建消费者实例
//            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
//            //设置消费者的Nameserver地址
//            consumer.setNamesrvAddr(NAME_SRV_ADD);
//            //订阅主题和标签
//            consumer.subscribe(COMMON_TOPIC, PHONE_CODE_TAG);
//            //设置消息监听器，处理接收到的消息
//            consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
//                for (MessageExt msg : list) {
//                    log.info("消费信息：{}", new String(msg.getBody()));
//                }
//                //返回消费成功状态
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            });
//            //启动消费者
//            consumer.start();
//        }
//    }
//}