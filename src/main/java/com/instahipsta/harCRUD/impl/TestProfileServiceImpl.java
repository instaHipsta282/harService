package com.instahipsta.harCRUD.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.domain.Request;
import com.instahipsta.harCRUD.domain.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.asList;

@Service
public class TestProfileServiceImpl implements TestProfileService {

    private TestProfileRepo testProfileRepo;
    private RequestService requestService;
    private ObjectMapper objectMapper;

    @Autowired
    public TestProfileServiceImpl(TestProfileRepo testProfileRepo, RequestService requestService,
                                  ObjectMapper objectMapper) {
        this.testProfileRepo = testProfileRepo;
        this.requestService = requestService;
        this.objectMapper = objectMapper;
    }

    @Override
    public TestProfile save(TestProfile testProfile) {
        int requestsCount =
                testProfile.getRequests() == null ? 0 : testProfile.getRequests().size();

        testProfile.setRequestsCount(requestsCount);
        return testProfileRepo.save(testProfile);
    }

    @Override
    public TestProfile create() {
        return new TestProfile();
    }

    @Override
    public TestProfile create(List<Request> requests) {
        return new TestProfile(requests);
    }

    @Override
    public TestProfile harToTestProfile(byte[] har) {
        TestProfile testProfile = create(new ArrayList<>());
        JsonNode entries = null;
        try {
            entries = objectMapper.readTree(har).path("log").path("entries");
        }
        catch (IOException e) { e.printStackTrace(); }

        for (JsonNode entry : entries) {
            Request req = entryToRequest(entry, testProfile);
            testProfile.getRequests().add(req);
        }

        return save(testProfile);
    }

    @Override
    public Request entryToRequest(JsonNode entry, TestProfile testProfile) {
        JsonNode request = entry.path("request");

        String url = request.path("url").asText();
        String body = request.path("postData").asText();
        HttpMethod method = HttpMethod.valueOf(request.path("method").asText());
        Map<String, String> headers = requestService.getMapValues(request.path("headers"));
        Map<String, String> params = requestService.getMapValues(request.path("queryString"));

        return requestService.create(url, body, headers, params, method, testProfile);
    }

}
