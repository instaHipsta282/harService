package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.impl.RequestServiceImpl;
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestServiceTest {

    @Autowired
    private RequestServiceImpl requestService;

    @MockBean
    private RequestRepo requestRepo;

    static Stream<Arguments> getMapValueWithArraySource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File( "filesForTests/test.json");
        JsonNode node = objectMapper.readTree(file).path("headers");

        return Stream.of(Arguments.of(node));
    }

    @ParameterizedTest
    @MethodSource("getMapValueWithArraySource")
    void getMapValuesWithArrayTest(JsonNode node) {
        Map<String, String> map = requestService.getMapValues(node);

        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals("Sun, 01 Dec 2019 21:32:09 GMT", map.get("Last-Modified"));
        Assertions.assertEquals("video/webm", map.get("Content-Type"));
        Assertions.assertEquals("Mon, 02 Dec 2019 13:59:15 GMT", map.get("Date"));
    }

    static Stream<Arguments> getMapValueWithoutArraySource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File( "filesForTests/test3.json");
        JsonNode node = objectMapper.readTree(file).path("headers");

        return Stream.of(Arguments.of(node));
    }

    @ParameterizedTest
    @MethodSource("getMapValueWithoutArraySource")
    void getMapValuesWithoutArrayTest(JsonNode node) throws Exception {
        Map<String, String> map = requestService.getMapValues(node);
        Assertions.assertEquals("Sun, 01 Dec 2019 21:32:09 GMT", map.get("Last-Modified"));
    }

    static Stream<Arguments> saveSource() {
        String url = "https://yandex.ru";
        String body = "{}";
        HttpMethod httpMethod = HttpMethod.GET;
        Map<String, String> headers = Maps.newHashMap("header1", "hvalue1");
        Map<String, String> params = Maps.newHashMap("param1", "value1");
        TestProfile testProfile = new TestProfile();

        return Stream.of(
                Arguments.of(new Request(0, url, body, headers, params, httpMethod, 0.0, testProfile)));
    }

    @ParameterizedTest
    @MethodSource("saveSource")
    void saveTest(Request request) {
        doReturn(request).when(requestRepo).save(request);
        Request savedRequest = requestService.save(request);
        Assertions.assertEquals(savedRequest, request);
    }

    static Stream<Arguments> entryToRequestSource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File( "filesForTests/test2.json");
        JsonNode node = objectMapper.readTree(file);

        return Stream.of(Arguments.of(node));
    }

    @ParameterizedTest
    @MethodSource("entryToRequestSource")
    void entryToRequestTest(JsonNode node) {
        Request request = requestService.entryToRequest(node, new TestProfile());

        Assertions.assertNotNull(request.getTestProfile());
        Assertions.assertEquals("https://e.mail.ru/api/v1/utils/xray/batch", request.getUrl());
        Assertions.assertEquals(HttpMethod.POST, request.getMethod());
        Assertions.assertEquals(0.0, request.getPerc());
        Assertions.assertEquals("e.mail.ru", request.getHeaders().get("Host"));
        Assertions.assertEquals("octavius", request.getParams().get("p"));
        Assertions.assertEquals("boddy", request.getBody());
    }

    static Stream<Arguments> jsonNodeToRequestListSource() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File( "filesForTests/test_archive.har");
        JsonNode node = objectMapper.readTree(file)
                .path("log")
                .path("entries");

        return Stream.of(Arguments.of(node));
    }


    @ParameterizedTest
    @MethodSource("jsonNodeToRequestListSource")
    void jsonNodeToRequestListTest(JsonNode node) {
        List<Request> requests = requestService.jsonNodeToRequestList(node);
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
