package com.instahipsta.harCRUD.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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

