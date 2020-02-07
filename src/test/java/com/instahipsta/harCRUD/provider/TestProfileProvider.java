package com.instahipsta.harCRUD.provider;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import org.assertj.core.util.Maps;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class TestProfileProvider {

    static List<Request> getListOfRequests() {
        String url = "https://yandex.ru";
        String body = "{}";
        HttpMethod httpMethod = HttpMethod.GET;
        Map<String, String> headers = Maps.newHashMap("header1", "hvalue1");
        Map<String, String> params = Maps.newHashMap("param1", "value1");
        TestProfile testProfile = new TestProfile();

        return asList(new Request(1L, url, body, headers, params, httpMethod, 0.0, testProfile));
    }

    static Stream<Arguments> testProfileSource() {
        TestProfile testProfile = new TestProfile();
        testProfile.setRequests(getListOfRequests());

        return Stream.of(Arguments.of(testProfile));
    }

    static Stream<Arguments> requestsSource() {
        return Stream.of(Arguments.of(getListOfRequests()));
    }

    static Stream<Arguments> testProfileWithoutRequestsSource() {
        return Stream.of(Arguments.of(new TestProfile()));
    }
}
