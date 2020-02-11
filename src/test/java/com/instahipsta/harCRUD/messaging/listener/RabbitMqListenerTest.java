package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.model.dto.HARDto;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class RabbitMqListenerTest {

    @Autowired
    private RabbitMqListener rabbitMqListener;

    @MockBean
    private RequestService requestService;

    @MockBean
    private TestProfileService testProfileService;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harDtoSource")
    void harWorkerTest(HARDto dto) {
        Assertions.assertDoesNotThrow(() -> rabbitMqListener.harWorker(dto));
    }
}
