package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.impl.RequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @InjectMocks
    private RequestServiceImpl requestService;
    @Mock
    private RequestRepo requestRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${file.filesForTests}")
    private String filesForTests;
    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private HttpMethod httpMethod;
    private TestProfile testProfile = new TestProfile();

    @BeforeEach
    public void initFields() {
        url = "https://yandex.ru";
        body = "{}";
        httpMethod = HttpMethod.GET;

        headers.put("header1", "hvalue1");
        headers.put("header2", "hvalue2");

        params.put("param1", "value1");
        params.put("param2", "value2");
    }

    @Test
    public void getMapValuesWithArrayTest() throws Exception {
        File file = new File(filesForTests + "/test.json");
        JsonNode node = objectMapper.readTree(file).path("headers");
        Map<String, String> map = requestService.getMapValues(node);

        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals("Sun, 01 Dec 2019 21:32:09 GMT", map.get("Last-Modified"));
        Assertions.assertEquals("video/webm", map.get("Content-Type"));
        Assertions.assertEquals("Mon, 02 Dec 2019 13:59:15 GMT", map.get("Date"));
    }

    @Test
    public void getMapValuesWithoutArrayTest() throws Exception {
        File file = new File(filesForTests + "/test3.json");
        JsonNode node = objectMapper.readTree(file).path("headers");
        Map<String, String> map = requestService.getMapValues(node);
        Assertions.assertEquals("Sun, 01 Dec 2019 21:32:09 GMT", map.get("Last-Modified"));
    }

    @Test
    public void createTest() {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);

        Assertions.assertEquals("https://yandex.ru", request.getUrl());
        Assertions.assertEquals("{}", request.getBody());
        Assertions.assertEquals(HttpMethod.GET, request.getMethod());
        Assertions.assertEquals(testProfile, request.getTestProfile());
        Assertions.assertEquals("hvalue2", request.getHeaders().get("header2"));
        Assertions.assertEquals("hvalue1", request.getHeaders().get("header1"));
        Assertions.assertEquals("value2", request.getParams().get("param2"));
        Assertions.assertEquals("value1", request.getParams().get("param1"));
    }

    @Test
    public void saveTest() {
        Request request = requestService.create(url, body, headers, params, httpMethod, testProfile);

        doReturn(request).when(requestRepo).save(request);
        Request savedRequest = requestService.save(request);
        Assertions.assertEquals(savedRequest, request);
    }

    @Test
    public void entryToRequestTest() throws Exception {
        File file = new File(filesForTests + "/test2.json");
        JsonNode entry = objectMapper.readTree(file);
        Request request = requestService.entryToRequest(entry, new TestProfile());
        Assertions.assertNotNull(request.getTestProfile());
        Assertions.assertEquals("https://e.mail.ru/api/v1/utils/xray/batch", request.getUrl());
        Assertions.assertEquals(HttpMethod.POST, request.getMethod());
        Assertions.assertEquals(0.0, request.getPerc());
        Assertions.assertEquals("e.mail.ru", request.getHeaders().get("Host"));
        Assertions.assertEquals("octavius", request.getParams().get("p"));
        Assertions.assertEquals("boddy", request.getBody());
    }

    @Test
    public void jsonNodeToRequestList() throws Exception {
        File file = new File(filesForTests + "/test_archive.har");
        JsonNode entries = objectMapper.readTree(file)
                .path("log")
                .path("entries");
        List<Request> requests = requestService.jsonNodeToRequestList(entries);
        Request request = requests.get(0);

        Assertions.assertEquals(1, requests.size());
        Assertions.assertEquals(HttpMethod.GET, request.getMethod());
        Assertions.assertEquals(0.0, request.getPerc());
        Assertions.assertEquals("https://www.youtube.com/?gl=RU", request.getUrl());
        Assertions.assertNotNull(request.getTestProfile());
        Assertions.assertEquals(10, request.getHeaders().size());
        Assertions.assertEquals(1, request.getParams().size());
    }
}
