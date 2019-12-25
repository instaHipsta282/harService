package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.domain.Request;
import com.instahipsta.harCRUD.domain.TestProfile;

import java.util.List;

public interface TestProfileService {

    TestProfile save(TestProfile testProfile);

    TestProfile create();

    TestProfile create(List<Request> requests);

    TestProfile harToTestProfile(byte[] har);
}
