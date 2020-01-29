package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestProfileService {

    TestProfile save(TestProfile testProfile);

    TestProfile create();

    TestProfile create(List<Request> requests);

    TestProfile harToTestProfile(byte[] har);

    Request entryToRequest(JsonNode entry, TestProfile testProfile);
}
