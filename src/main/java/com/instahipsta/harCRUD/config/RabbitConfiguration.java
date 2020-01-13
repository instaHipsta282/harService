package com.instahipsta.harCRUD.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue harQueue() {
        return new Queue("harQueue");
    }

    @Bean
    public DirectExchange directExchange() { return new DirectExchange("harExchange"); }

    @Bean
    public Binding harBinding() {
        return BindingBuilder.bind(harQueue()).to(directExchange()).with("har");
    }

}

