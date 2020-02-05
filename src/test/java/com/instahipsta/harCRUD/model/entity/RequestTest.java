package com.instahipsta.harCRUD.model.entity;

import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
public class RequestTest {

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
    private Request request;

    @BeforeEach
    void initFields() {
        url = "https://yandex.ru";
        body = "{}";
        httpMethod = HttpMethod.GET;
        testProfile = new TestProfile();

        testProfile = testProfileService.save(testProfile);

        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");
        this.request = requestService.create(url, body, headers, params, httpMethod, testProfile);
    }

    @Test
    void constructorWithoutArguments() {
        Request request = new Request();
        Assertions.assertNotNull(request);
    }

    @Test
    void constructorWithArguments() {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);
        Assertions.assertEquals("hvalue1", request.getHeaders().get("header1"));
        Assertions.assertEquals("hvalue2", request.getHeaders().get("header2"));
        Assertions.assertEquals("value1", request.getParams().get("param1"));
        Assertions.assertEquals("value2", request.getParams().get("param2"));
        Assertions.assertNotNull(request.getTestProfile());
        Assertions.assertEquals("https://yandex.ru", request.getUrl());
        Assertions.assertEquals(0.0, request.getPerc());
        Assertions.assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void getIdTest() {
        Assertions.assertEquals(0, request.getId());
    }

    @Test
    void getUrlTest() {
        Assertions.assertEquals("https://yandex.ru", request.getUrl());
    }

    @Test
    void setUrlTest() {
        request.setUrl("hohoh");
        Assertions.assertEquals("hohoh", request.getUrl());
    }

    @Test
    void getBodyTest() {
        Assertions.assertEquals("{}", request.getBody());
    }

    @Test
    void setBodyTest() {
        request.setBody("body");
        Assertions.assertEquals("body", request.getBody());
    }

    @Test
    void getHeadersTest() {
        Assertions.assertEquals(2, request.getHeaders().size());
    }

    @Test
    void setHeadersTest() {
        Map<String, String> newHeaders = new HashMap<>();
        newHeaders.put("header1k", "header1v");
        newHeaders.put("header2k", "header2v");
        newHeaders.put("header3k", "header3v");
        request.setHeaders(newHeaders);
        Assertions.assertEquals(3, request.getHeaders().size());
    }

    @Test
    void getParamsTest() {
        Assertions.assertEquals(2, request.getParams().size());
    }

    @Test
    void setParamsTest() {
        Map<String, String> newParams = new HashMap<>();
        newParams.put("param1k", "param1v");
        newParams.put("param2k", "param2v");
        newParams.put("param3k", "param3v");
        request.setParams(newParams);
        Assertions.assertEquals(3, request.getParams().size());
    }

    @Test
    void getMethodTest() {
        Assertions.assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    void setMethodTest() {
        request.setMethod(HttpMethod.DELETE);
        Assertions.assertEquals(HttpMethod.DELETE, request.getMethod());
    }

    @Test
    void getPercTest() {
        Assertions.assertEquals((Double) 0.0, request.getPerc());
    }

    @Test
    void setPercTest() {
        request.setPerc(0.5);
        Assertions.assertEquals((Double) 0.5, request.getPerc());
    }

    @Test
    void getTestProfileTest() {
        Assertions.assertNotNull(request.getTestProfile());
    }

    @Test
    void setTestProfileTest() {
        List<Request> requests = new ArrayList<>();
        requests.add(requestService.create("yandex.ru", body, headers, params, httpMethod, testProfile));
        requests.add(requestService.create("google.com", body, headers, params, httpMethod, testProfile));
        requests.add(requestService.create("rambler.ru", body, headers, params, httpMethod, testProfile));
        TestProfile newTestProfile = testProfileService.create(requests);
        request.setTestProfile(newTestProfile);
        Assertions.assertEquals(3, request.getTestProfile().getRequests().size());
    }

}
