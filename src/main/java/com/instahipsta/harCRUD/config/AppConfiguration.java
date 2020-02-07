package com.instahipsta.harCRUD.config;

import com.instahipsta.harCRUD.config.property.RabbitmqProperties;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
@EnableRabbit
public class AppConfiguration {

    private RabbitmqProperties rabbitmqProperties;

    public AppConfiguration(RabbitmqProperties rabbitmqProperties) {
        this.rabbitmqProperties = rabbitmqProperties;
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    @Bean
    public DirectExchange deadletterExchange() {
        return new DirectExchange(rabbitmqProperties.getDeadletterExchange(), true, false);
    }

    @Bean
    public Queue deadletterQueue() {
        return new Queue(rabbitmqProperties.getDeadletterQueue(), true, false, false, null);
    }

    @Bean
    public Binding deadletterBinding() {
        return new Binding(rabbitmqProperties.getDeadletterQueue(),
                Binding.DestinationType.QUEUE,
                rabbitmqProperties.getDeadletterExchange(),
                rabbitmqProperties.getDeadletterRoutingKey(),
                null);
    }

    @Bean
    public DirectExchange harExchange() {
        return new DirectExchange(rabbitmqProperties.getHarExchange(), true, false);
    }

    @Bean
    public Queue harQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", rabbitmqProperties.getDeadletterExchange());
        args.put("x-dead-letter-routing-key", rabbitmqProperties.getDeadletterRoutingKey());
        return new Queue(rabbitmqProperties.getHarQueue(), true, false, false, args);
    }

    @Bean
    public Binding binding() {
        return new Binding(rabbitmqProperties.getHarQueue(),
                Binding.DestinationType.QUEUE,
                rabbitmqProperties.getHarExchange(),
                rabbitmqProperties.getHarRoutingKey(), null);
    }
}
