package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.impl.TestProfileServiceImpl;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProfileServiceTest {

    @Autowired
    private TestProfileServiceImpl testProfileService;

    @MockBean
    private TestProfileRepo testProfileRepo;

    static Stream<Arguments> testSaveSource() {
        String url = "https://yandex.ru";
        String body = "{}";
        HttpMethod httpMethod = HttpMethod.GET;
        Map<String, String> headers = Maps.newHashMap("header1", "hvalue1");
        Map<String, String> params = Maps.newHashMap("param1", "value1");
        TestProfile testProfile = new TestProfile();

        Request request = new Request(1L, url, body, headers, params, httpMethod, 0.0, testProfile);
        testProfile.setRequests(Collections.singletonList(request));

        return Stream.of(Arguments.of(testProfile));
    }

    @ParameterizedTest
    @MethodSource("testSaveSource")
    void saveTest(TestProfile testProfile) {

        doReturn(testProfile).when(testProfileRepo).save(testProfile);

        TestProfile savedTestProfile = testProfileService.save(testProfile);
        Assertions.assertEquals(testProfile.getRequests().size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(testProfile.getRequests(), savedTestProfile.getRequests());
        Assertions.assertEquals(testProfile.getId(), savedTestProfile.getId());
    }

    static Stream<Arguments> testSaveWithoutRequestsSource() {
        return Stream.of(Arguments.of(new TestProfile()));
    }

    @ParameterizedTest
    @MethodSource("testSaveWithoutRequestsSource")
    void saveWithoutRequestsTest(TestProfile testProfile) {
        doReturn(testProfile).when(testProfileRepo).save(testProfile);

        TestProfile savedTestProfile = testProfileService.save(testProfile);

        Assertions.assertEquals(0, savedTestProfile.getRequestsCount());
        Assertions.assertNotNull(savedTestProfile.getRequests());
    }

    static Stream<Arguments> testSaveWithRequestsSource() {
        String url = "https://yandex.ru";
        String body = "{}";
        HttpMethod httpMethod = HttpMethod.GET;
        Map<String, String> headers = Maps.newHashMap("header1", "hvalue1");
        Map<String, String> params = Maps.newHashMap("param1", "value1");
        TestProfile testProfile = new TestProfile();

        Request request = new Request(1L, url, body, headers, params, httpMethod, 0.0, testProfile);

        testProfile.setRequestsCount(1);
        testProfile.setRequests(asList(request));
        return Stream.of(Arguments.of(asList(request), testProfile));
    }

    @ParameterizedTest
    @MethodSource("testSaveWithRequestsSource")
    void saveWithRequestsTest(List<Request> requests, TestProfile testProfile) {
        doReturn(testProfile).when(testProfileRepo).save(testProfile);

        TestProfile savedTestProfile = testProfileService.save(requests);

        Assertions.assertEquals(requests.size(), savedTestProfile.getRequestsCount());
        Assertions.assertEquals(requests, savedTestProfile.getRequests());
    }
}
