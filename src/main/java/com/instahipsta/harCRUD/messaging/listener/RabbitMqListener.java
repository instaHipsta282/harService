package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.domain.TestProfile;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMqListener {

    private TestProfileService testProfileService;

    @Autowired
    public RabbitMqListener(TestProfileService testProfileService) {
        this.testProfileService = testProfileService;
    }

    @RabbitListener(queues = "harQueue")
    public void harWorker(String message) {
        try {
            testProfileService.harToTestProfile(message.getBytes());
        }
        catch (IOException e) { e.printStackTrace(); }
    }

}
