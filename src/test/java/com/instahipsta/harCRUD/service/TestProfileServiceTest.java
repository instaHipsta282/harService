package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.impl.TestProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProfileServiceTest {

    @Autowired
    private TestProfileServiceImpl testProfileService;

    @MockBean
    private TestProfileRepo testProfileRepo;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.TestProfileProvider#testProfileSource")
    void saveTest(TestProfile testProfile) {

        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(testProfile.getRequests().size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(testProfile.getRequests(), savedTestProfile.getRequests());
        Assertions.assertEquals(testProfile.getId(), savedTestProfile.getId());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.TestProfileProvider#testProfileWithoutRequestsSource")
    void saveWithoutRequestsTest(TestProfile testProfile) {
        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());


        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(0, savedTestProfile.getRequestsCount());
        Assertions.assertNotNull(savedTestProfile.getRequests());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.provider.TestProfileProvider#requestsSource")
    void saveWithRequestsTest(List<Request> requests) {
        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        TestProfile savedTestProfile = testProfileService.save(requests);

        Assertions.assertEquals(requests.size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(requests, savedTestProfile.getRequests());
    }
}
