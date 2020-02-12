package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RabbitMqListener {

    private TestProfileService testProfileService;
    private RequestService requestService;

    public RabbitMqListener(TestProfileService testProfileService,
                            RequestService requestService) {

        this.testProfileService = testProfileService;
        this.requestService = requestService;
    }

    @RabbitListener(queues = "${rabbitmq.harQueue}")
    public void harWorker(HARDto dto) {
        List<Request> requests = requestService.harDtoToRequestList(dto);
        testProfileService.save(requests);
    }
}
