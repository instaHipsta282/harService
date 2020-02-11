package com.instahipsta.harCRUD.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.dto.Har.Entry;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import org.junit.jupiter.params.provider.Arguments;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class TestProfileProvider {

    static Request getRequest() throws IOException {
        File file = new File("filesForTests/test4.json");
        ObjectMapper objectMapper = new ObjectMapper();
        ModelMapper modelMapper = new ModelMapper();
        Entry entry = objectMapper.readValue(file, Entry.class);
        Request request = modelMapper.map(entry.getRequestDto(), Request.class);
        return request;
    }
//
//    static List<Request> getListOfRequests() {
//        String url = "https://yandex.ru";
//        String body = "{}";
//        HttpMethod httpMethod = HttpMethod.GET;
//        Map<String, String> headers = Maps.newHashMap("header1", "hvalue1");
//        Map<String, String> params = Maps.newHashMap("param1", "value1");
//        TestProfile testProfile = new TestProfile();
//
//        return asList( new Request(1L, url, body, headers, params, httpMethod, 0.0, testProfile));
//    }

    static Stream<Arguments> testProfileSource() throws IOException {
        TestProfile testProfile = new TestProfile();
        testProfile.setRequests(asList(getRequest()));

        return Stream.of(Arguments.of(testProfile));
    }

    static Stream<Arguments> requestsSource() throws IOException {
        return Stream.of(Arguments.of(asList(getRequest())));
    }

    static Stream<Arguments> testProfileWithoutRequestsSource() {
        return Stream.of(Arguments.of(new TestProfile()));
    }
}
