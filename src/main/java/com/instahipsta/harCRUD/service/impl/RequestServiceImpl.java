package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepo requestRepo;

    @Autowired
    public RequestServiceImpl(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    @Override
    public Map<String, String> getMapValues(JsonNode mapValues) {
        Map<String, String> map = new HashMap<>();
        if (mapValues.isArray()) {
            mapValues.forEach(entry -> map
                            .put(entry.path("name").asText(), entry.path("value").asText()));
        }
        return map;
    }

    @Override
    public Request create(String url,
                          String body,
                          Map<String, String> headers,
                          Map<String, String> params,
                          HttpMethod method,
                          TestProfile testProfile) {

        return new Request(url, body, headers, params, method, testProfile);
    }

    @Override
    public Request save(Request request) {
        return requestRepo.save(request);
    }
}
