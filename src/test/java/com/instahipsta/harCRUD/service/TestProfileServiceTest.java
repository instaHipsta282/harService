package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.impl.TestProfileServiceImpl;
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
public class TestProfileServiceTest {

    @InjectMocks
    private TestProfileServiceImpl testProfileService;

    @Mock
    private TestProfileRepo testProfileRepo;

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.TestProfileArgs#testProfileSource")
    void saveTest(TestProfile testProfile) {

        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(testProfile.getRequests().size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(testProfile.getRequests(), savedTestProfile.getRequests());
        Assertions.assertEquals(testProfile.getId(), savedTestProfile.getId());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.TestProfileArgs#testProfileWithoutRequestsSource")
    void saveWithoutRequestsTest(TestProfile testProfile) {
        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());


        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(0, savedTestProfile.getRequestsCount());
        Assertions.assertNotNull(savedTestProfile.getRequests());
    }

    @ParameterizedTest
    @MethodSource("com.instahipsta.harCRUD.arg.TestProfileArgs#requestsSource")
    void saveWithRequestsTest(List<Request> requests) {
        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        TestProfile savedTestProfile = testProfileService.save(requests);

        Assertions.assertEquals(requests.size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(requests, savedTestProfile.getRequests());
    }
}
