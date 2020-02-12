package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.impl.RequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @InjectMocks
    private RequestServiceImpl requestService;

    @Mock
    private RequestRepo requestRepo;

    @Mock
    private ModelMapper modelMapper;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.HARArgs#harDtoSource")
    void harDtoToRequestListTest(HARDto harDto) {
        when(requestRepo.save(any(Request.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        List<Request> requests = requestService.harDtoToRequestList(harDto);

        Assertions.assertEquals(1, requests.size());
        Assertions.assertEquals(HttpMethod.GET, requests.get(0).getMethod());
        Assertions.assertEquals("https://www.youtube.com/?gl=RU", requests.get(0).getUrl());
    }
}
