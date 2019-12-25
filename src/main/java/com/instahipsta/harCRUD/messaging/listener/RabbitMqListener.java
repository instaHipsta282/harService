package com.instahipsta.harCRUD.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    @RabbitListener(queues = "harQueue")
    public void harWorker(String message) {
        System.out.println(message);
    }

}
