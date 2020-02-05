package com.instahipsta.harCRUD.messaging.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitMqListener {

    private TestProfileService testProfileService;
    private RequestService requestService;

    @RabbitListener(queues = "${rabbitmq.harQueue}")
    public void harWorker(JsonNode message) {
        List<Request> requests = requestService.jsonNodeToRequestList(message);
        testProfileService.save(requests);
    }
}
