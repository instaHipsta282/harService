package com.instahipsta.harCRUD.config;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
@EnableRabbit
public class AppConfiguration {

    @Value("${rabbitmq.harQueue}")
    private String harQueue;
    @Value("${rabbitmq.deadletterQueue}")
    private String deadletterQueue;
    @Value("${rabbitmq.harExchange}")
    private String harExchange;
    @Value("${rabbitmq.deadletterExchange}")
    private String deadletterExchange;
    @Value("${rabbitmq.harRoutingKey}")
    private String harRoutingKey;
    @Value("${rabbitmq.deadletterRoutingKey}")
    private String deadletterRoutingKey;

    @Bean
    public ModelMapper mapper() {
//
//        mapper.createTypeMap(Request.class, RequestDTO.class)
//                .addMappings(m -> m.skip(RequestDTO::setTestProfileId)).setPostConverter(toDtoConverter());
//        mapper.createTypeMap(RequestDTO.class, Request.class)
//                .addMappings(m -> m.skip(Request::setTestProfile)).setPostConverter(toEntityConverter());

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
        return new DirectExchange(deadletterExchange, true, false);
    }

    @Bean
    public Queue deadletterQueue() {
        return new Queue(deadletterQueue, true, false, false, null);
    }

    @Bean
    public Binding deadletterBinding() {
        return new Binding(deadletterQueue,
                Binding.DestinationType.QUEUE,
                deadletterExchange,
                deadletterRoutingKey,
                null);
    }

    @Bean
    public DirectExchange harExchange() {
        return new DirectExchange(harExchange, true, false);
    }

    @Bean
    public Queue harQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", deadletterExchange);
        args.put("x-dead-letter-routing-key", deadletterRoutingKey);
        return new Queue(harQueue, true, false, false, args);
    }

    @Bean
    public Binding binding() {
        return new Binding(harQueue, Binding.DestinationType.QUEUE, harExchange, harRoutingKey, null);
    }
}
