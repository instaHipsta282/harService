package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMqListener {

    private TestProfileService testProfileService;

    @RabbitListener(queues = "harQueue")
    public void harWorker(String message) {
        testProfileService.harToTestProfile(message.getBytes());
    }
}
