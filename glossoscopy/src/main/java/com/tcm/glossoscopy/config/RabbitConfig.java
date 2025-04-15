package com.tcm.glossoscopy.config;

import com.rabbitmq.client.ConnectionFactory;
import com.tcm.glossoscopy.entity.properties.RabbitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {
    @Bean
    public ConnectionFactory rabbitConnectionFactory(RabbitProperties rabbitProperties) {
        log.info("开始创建rabbit连接工厂");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setPort(rabbitProperties.getPort());
        return connectionFactory;
    }
}