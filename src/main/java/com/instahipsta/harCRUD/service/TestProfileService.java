package com.instahipsta.harCRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;

import java.util.List;

public interface TestProfileService {

    TestProfile save(TestProfile testProfile);

    TestProfile create();

    TestProfile create(List<Request> requests);

}
