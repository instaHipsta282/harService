package com.instahipsta.harCRUD.model.entity;

import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@SpringBootTest
public class TestProfileTest {

    @Autowired
    private TestProfileService testProfileService;
    @Autowired
    private RequestService requestService;
    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private HttpMethod httpMethod;
    private TestProfile testProfile;
    private TestProfile fullTestProfile;

    @BeforeEach
    public void initFields() {
        url = "https://yandex.ru";
        body = "{}";
        httpMethod = HttpMethod.GET;
        testProfile = new TestProfile();

        testProfile = testProfileService.save(testProfile);

        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        fullTestProfile = testProfileService.create(asList(request));
    }

    @Test
    public void constructorWithoutParametersTest() {
        TestProfile testProfile = new TestProfile();
        Assertions.assertNotNull(testProfile);
    }

    @Test
    public void constructorWithParametersTest() {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        TestProfile testProfile = testProfileService.create(asList(request));

        Request newRequest = testProfile.getRequests().get(0);
        Assertions.assertEquals("hvalue1", newRequest.getHeaders().get("header1"));
        Assertions.assertEquals("hvalue2", newRequest.getHeaders().get("header2"));
        Assertions.assertEquals("value1", newRequest.getParams().get("param1"));
        Assertions.assertEquals("value2", newRequest.getParams().get("param2"));
        Assertions.assertNotNull(newRequest.getTestProfile());
        Assertions.assertEquals("https://yandex.ru", newRequest.getUrl());
        Assertions.assertEquals(0.0, newRequest.getPerc());
        Assertions.assertEquals(HttpMethod.GET, newRequest.getMethod());
    }

    @Test
    public void getIdTest() {
        Long id = testProfile.getId();
        Assertions.assertNotNull(id);
    }

    @Test
    public void getRequestsTest() throws Exception {
        Assertions.assertEquals(1, fullTestProfile.getRequests().size());
    }

    @Test
    public void setRequestsTest() throws Exception {
        Request request = requestService.create(url, "{body}", headers, params, httpMethod, testProfile);
        testProfile.setRequests(asList(request));
        Assertions.assertEquals("{body}", testProfile.getRequests().get(0).getBody());
    }

    @Test
    public void getRequestsCountTest() throws Exception {
        Assertions.assertEquals(1, fullTestProfile.getRequestsCount());
    }

    @Test
    public void setRequestsCountTest() throws Exception {
        fullTestProfile.setRequestsCount(561);
        Assertions.assertEquals(561, fullTestProfile.getRequestsCount());
    }
}
