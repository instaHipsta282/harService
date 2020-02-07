package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.RequestService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepo requestRepo;

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
        else {
            map.put(mapValues.path("name").asText(), mapValues.path("value").asText());
        }
        return map;
    }

    @Override
    public Request save(Request request) {
        return requestRepo.save(request);
    }


    @Override
    public Request entryToRequest(JsonNode entry,
                                  TestProfile testProfile) {

        String url = entry.at("/request/url").asText();
        String body = entry.at("/request/postData/text").asText();
        HttpMethod method = HttpMethod.valueOf(entry.at("/request/method").asText());
        Map<String, String> headers = getMapValues(entry.at("/request/headers"));
        Map<String, String> params = getMapValues(entry.at("/request/queryString"));

        return new Request(0, url, body, headers, params, method, 0.0, testProfile);
    }

    @Override
    public List<Request> jsonNodeToRequestList(JsonNode entries) {
        List<Request> requests = new ArrayList<>();
        for (JsonNode entry : entries) {
            Request req = entryToRequest(entry, new TestProfile());
            requests.add(req);
        }
        return requests;
    }
}
