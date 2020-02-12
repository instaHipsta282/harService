package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RabbitMqListenerTest {

    @InjectMocks
    private RabbitMqListener rabbitMqListener;

    @Mock
    private RequestService requestService;

    @Mock
    private TestProfileService testProfileService;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoSource")
    void harWorkerTest(HARDto dto) {
        Assertions.assertDoesNotThrow(() -> rabbitMqListener.harWorker(dto));
    }
}
