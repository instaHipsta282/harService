package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.impl.TestProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestProfileServiceTest {

    @InjectMocks
    private TestProfileServiceImpl testProfileService;
    @Mock
    private TestProfileRepo testProfileRepo;
    @Autowired
    private RequestService requestService;
    private static String url;
    private static String body;
    private static HttpMethod httpMethod;
    private static Map<String, String> headers = new HashMap<>();
    private static Map<String, String> params = new HashMap<>();

    @BeforeAll
    public static void initFields() {
        MockitoAnnotations.initMocks(TestProfileServiceTest.class);

        url = "https://yandex.ru";
        body = "{}";
        httpMethod = HttpMethod.GET;
        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");
    }

    @Test
    public void saveWithRequestsTest() {
        TestProfile testProfile = testProfileService.create(new ArrayList<>());
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        testProfile.getRequests().add(request);

        doReturn(testProfile).when(testProfileRepo).save(testProfile);

        TestProfile savedTestProfile = testProfileService.save(testProfile);
        assertThat(testProfile).isEqualTo(savedTestProfile);
    }

    @Test
    public void saveWithoutRequestsTest() {
        TestProfile testProfile = testProfileService.create(new ArrayList<>());

        doReturn(testProfile).when(testProfileRepo).save(testProfile);

        TestProfile savedTestProfile = testProfileService.save(testProfile);
        assertThat(testProfile).isEqualTo(savedTestProfile);
    }

    @Test
    public void createWithoutArgsTest() {
        TestProfile testProfile = testProfileService.create();
        Assertions.assertNotNull(testProfile);
        Assertions.assertNull(testProfile.getRequests());
        Assertions.assertEquals(0, testProfile.getRequestsCount());
    }

    @Test
    public void createWithArgsTest() {
        List<Request> requests = asList(requestService
                .create(url, body, headers, params, httpMethod, new TestProfile()));

        TestProfile testProfile = testProfileService.create(requests);

        Assertions.assertNotNull(testProfile);
        Assertions.assertNotNull(testProfile.getRequests());
        Assertions.assertEquals(1, testProfile.getRequestsCount());
    }
}
