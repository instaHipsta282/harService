package com.instahipsta.harCRUD.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.RequestService;
import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class TestProfileServiceImpl implements TestProfileService {

    private TestProfileRepo testProfileRepo;
    private RequestService requestService;
    private ObjectMapper objectMapper;
    private RabbitTemplate rabbitTemplate;

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
        TestProfile testProfile = new TestProfile();
        testProfile.setRequests(requests);
        testProfile.setRequestsCount(requests.size());
        return testProfile;
    }

    @Override
    public TestProfile harToTestProfile(byte[] har) {
        TestProfile testProfile = create(new ArrayList<>());
        JsonNode entries = null;
        try {
            entries = objectMapper.readTree(har).path("log").path("entries");
        }
        catch (IOException e) {
            rabbitTemplate.convertAndSend("error", e.getMessage());
            log.error("Failed to JSON parse", e);
        }

        for (JsonNode entry : entries) {
            Request req = entryToRequest(entry, testProfile);
            testProfile.getRequests().add(req);
        }

        return save(testProfile);
    }

    @Override
    public Request entryToRequest(JsonNode entry,
                                  TestProfile testProfile) {

        JsonNode request = entry.path("request");

        String url = request.path("url").asText();
        String body = request.path("postData").asText();
        HttpMethod method = HttpMethod.valueOf(request.path("method").asText());
        Map<String, String> headers = requestService.getMapValues(request.path("headers"));
        Map<String, String> params = requestService.getMapValues(request.path("queryString"));

        return requestService.create(url, body, headers, params, method, testProfile);
    }

}
