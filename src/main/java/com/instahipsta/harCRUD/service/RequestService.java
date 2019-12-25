package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.domain.Request;
import com.instahipsta.harCRUD.domain.TestProfile;
import org.springframework.http.HttpMethod;

import java.util.Map;

public interface RequestService {
    Map<String, String> getMapValues(JsonNode mapValues);

    Request create(String url, String body, Map<String, String> headers,
                   Map<String, String> params, HttpMethod method, TestProfile testProfile);

    Request save(Request request);
}
