package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.impl.TestProfileServiceImpl;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProfileServiceTest {

    @Autowired
    private TestProfileServiceImpl testProfileService;

    @MockBean
    private TestProfileRepo testProfileRepo;

    static List<Request> getListOfRequests() {
        String url = "https://yandex.ru";
        String body = "{}";
        HttpMethod httpMethod = HttpMethod.GET;
        Map<String, String> headers = Maps.newHashMap("header1", "hvalue1");
        Map<String, String> params = Maps.newHashMap("param1", "value1");
        TestProfile testProfile = new TestProfile();

        return asList(new Request(1L, url, body, headers, params, httpMethod, 0.0, testProfile));
    }

    static Stream<Arguments> saveSource() {
        TestProfile testProfile = new TestProfile();
        testProfile.setRequests(getListOfRequests());

        return Stream.of(Arguments.of(testProfile));
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void saveTest(TestProfile testProfile) {

        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(testProfile.getRequests().size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(testProfile.getRequests(), savedTestProfile.getRequests());
        Assertions.assertEquals(testProfile.getId(), savedTestProfile.getId());
    }

    static Stream<Arguments> saveWithoutRequestsSource() {
        return Stream.of(Arguments.of(new TestProfile()));
    }

    @ParameterizedTest
    @MethodSource("saveWithoutRequestsSource")
    void saveWithoutRequestsTest(TestProfile testProfile) {
        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());


        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(0, savedTestProfile.getRequestsCount());
        Assertions.assertNotNull(savedTestProfile.getRequests());
    }

    @Test
    void saveWithRequestsTest() {
        List<Request> requests = getListOfRequests();
        when(testProfileRepo.save(any(TestProfile.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        TestProfile savedTestProfile = testProfileService.save(requests);

        Assertions.assertEquals(requests.size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(requests, savedTestProfile.getRequests());
    }
}
