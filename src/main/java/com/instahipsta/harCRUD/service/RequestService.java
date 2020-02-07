package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RequestService {
    Map<String, String> getMapValues(JsonNode mapValues);

    Request save(Request request);

    Request entryToRequest(JsonNode entry,
                           TestProfile testProfile);

    List<Request> jsonNodeToRequestList(JsonNode entries);
}
