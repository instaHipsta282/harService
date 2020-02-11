package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.instahipsta.harCRUD.model.dto.HARDto;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.impl.RequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestServiceTest {

    @Autowired
    private RequestServiceImpl requestService;

    @MockBean
    private RequestRepo requestRepo;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.HarProvider#harDtoSource")
    void harDtoToRequestListTest(HARDto harDto) throws JsonProcessingException {
        when(requestRepo.save(any(Request.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        List<Request> requests = requestService.harDtoToRequestList(harDto);

        Assertions.assertEquals(1, requests.size());
        Assertions.assertEquals(HttpMethod.GET, requests.get(0).getMethod());
        Assertions.assertEquals("https://www.youtube.com/?gl=RU", requests.get(0).getUrl());
    }
}
