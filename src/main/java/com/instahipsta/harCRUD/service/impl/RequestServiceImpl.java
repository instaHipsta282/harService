package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.RequestRepo;
import com.instahipsta.harCRUD.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private RequestRepo requestRepo;

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
    public Request create(String url,
                          String body,
                          Map<String, String> headers,
                          Map<String, String> params,
                          HttpMethod method,
                          TestProfile testProfile) {

        Request request = new Request();
        request.setUrl(url);
        request.setBody(body);
        request.setHeaders(headers);
        request.setParams(params);
        request.setMethod(method);
        request.setPerc(0.0);
        request.setTestProfile(testProfile);

        return request;
    }

    @Override
    public Request save(Request request) {
        return requestRepo.save(request);
    }


    @Override
    public Request entryToRequest(JsonNode entry,
                                  TestProfile testProfile) {
        JsonNode request = entry.path("request");

        String url = request.path("url").asText();
        String body = request.path("postData").path("text").asText();
        HttpMethod method = HttpMethod.valueOf(request.path("method").asText());
        Map<String, String> headers = getMapValues(request.path("headers"));
        Map<String, String> params = getMapValues(request.path("queryString"));
        return create(url, body, headers, params, method, testProfile);
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
