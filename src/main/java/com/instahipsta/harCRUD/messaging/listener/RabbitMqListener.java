package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.service.TestProfileService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    private TestProfileService testProfileService;

    @Autowired
    public RabbitMqListener(TestProfileService testProfileService) {
        this.testProfileService = testProfileService;
    }

    @RabbitListener(queues = "harQueue")
    public void harWorker(String message) {
        testProfileService.harToTestProfile(message.getBytes());
    }
}
