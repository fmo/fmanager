package com.fmo.fmanager;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    private static final String MY_QUEUE = "MyQueue";

    @Bean
    Queue myQueue() {
        return new Queue(MY_QUEUE, true);
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(myQueue())
                .to(myExchange())
                .with("topic")
                .noargs();
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.topicExchange("MyTopicExchange")
                .durable(true)
                .build();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cashingConnectionFactory = new CachingConnectionFactory("rabbit-1");
        cashingConnectionFactory.setUsername("guest");
        cashingConnectionFactory.setPassword("guest");

        return cashingConnectionFactory;
    }
}
