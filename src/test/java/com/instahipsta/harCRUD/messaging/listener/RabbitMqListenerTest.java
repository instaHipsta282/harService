package com.instahipsta.harCRUD.messaging.listener;

import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RabbitMqListenerTest {

    @InjectMocks
    private RabbitMqListener rabbitMqListener;

    @Mock
    private RequestService requestService;

    @Mock
    private TestProfileService testProfileService;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoAndRequestsSource")
    void harWorkerTest(HARDto dto, List<Request> requests) {
        when(requestService.harDtoToRequestList(any())).thenReturn(requests);
        when(testProfileService.save(requests)).thenReturn(TestProfile.builder().requests(requests).build());

        Assertions.assertDoesNotThrow(() -> rabbitMqListener.harWorker(dto));
    }
}
