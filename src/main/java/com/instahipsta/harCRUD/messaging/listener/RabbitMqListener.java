package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitMqListener {

    private TestProfileService testProfileService;

    @RabbitListener(queues = "${rabbitmq.harQueue}")
    public void harWorker(String message) {
        testProfileService.harToTestProfile(message.getBytes());
        log.info("Receiving message: {}", message.substring(0, 100));

    }
}
