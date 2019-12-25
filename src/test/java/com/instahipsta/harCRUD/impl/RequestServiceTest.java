package com.instahipsta.harCRUD.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.domain.Request;
import com.instahipsta.harCRUD.domain.TestProfile;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RequestServiceTest {

    @Autowired
    private TestProfileService testProfileService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ObjectMapper objectMapper;
    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private HttpMethod httpMethod;
    private TestProfile testProfile;

    @Before
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
    }

    @Test
    public void getMapValuesTest() throws Exception {
        File file = new File("test_downloads/test.json");
        JsonNode node = objectMapper.readTree(file).path("headers");
        Map<String, String> map = requestService.getMapValues(node);

        Assert.assertEquals(3, map.size());
        Assert.assertEquals("Sun, 01 Dec 2019 21:32:09 GMT", map.get("Last-Modified"));
        Assert.assertEquals("video/webm", map.get("Content-Type"));
        Assert.assertEquals("Mon, 02 Dec 2019 13:59:15 GMT", map.get("Date"));
    }

    @Test
    public void createTest() throws Exception {
       Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);

        Assert.assertEquals("https://yandex.ru", request.getUrl());
        Assert.assertEquals("{}", request.getBody());
        Assert.assertEquals(HttpMethod.GET, request.getMethod());
        Assert.assertEquals(testProfile, request.getTestProfile());
        Assert.assertEquals("hvalue2", request.getHeaders().get("header2"));
        Assert.assertEquals("hvalue1", request.getHeaders().get("header1"));
        Assert.assertEquals("value2", request.getParams().get("param2"));
        Assert.assertEquals("value1", request.getParams().get("param1"));
    }

    @Test
    public void saveTest() throws Exception {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);

        Request savedRequest = requestService.save(request);

        Assert.assertEquals("https://yandex.ru", savedRequest.getUrl());
        Assert.assertEquals("{}", savedRequest.getBody());
        Assert.assertEquals(HttpMethod.GET, savedRequest.getMethod());
        Assert.assertEquals(testProfile, savedRequest.getTestProfile());
        Assert.assertEquals("hvalue2", savedRequest.getHeaders().get("header2"));
        Assert.assertEquals("hvalue1", savedRequest.getHeaders().get("header1"));
        Assert.assertEquals("value2", savedRequest.getParams().get("param2"));
        Assert.assertEquals("value1", savedRequest.getParams().get("param1"));
    }
}
