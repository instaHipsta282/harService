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

@Service
public class TestProfileServiceImpl implements TestProfileService {

    private TestProfileRepo testProfileRepo;
    private RequestService requestService;

    @Autowired
    public TestProfileServiceImpl(TestProfileRepo testProfileRepo, RequestService requestService) {
        this.testProfileRepo = testProfileRepo;
        this.requestService = requestService;
    }

    @Override
    public TestProfile save(TestProfile testProfile) {
        int requestsCount = testProfile.getRequests() == null ? 0 : testProfile.getRequests().size();
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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode archive = null;

        try { archive = objectMapper.readTree(har); }
        catch (IOException e) { e.printStackTrace(); }

        JsonNode entries = archive.path("log").path("entries");

        if (entries.isArray()) {
            for (JsonNode entry : entries) {
                JsonNode request = entry.path("request");

                String url = request.path("url").asText();
                String body = request.path("postData").asText();
                HttpMethod method = HttpMethod.valueOf(request.path("method").asText());
                Map<String, String> headers = requestService.getMapValues(request.path("headers"));
                Map<String, String> params = requestService.getMapValues(request.path("queryString"));

                Request req = requestService.create(url, body, headers, params, method, testProfile);

                testProfile.getRequests().add(req);
            }
        }
        return save(testProfile);
    }
}
