package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestProfileService {

    TestProfile save(TestProfile testProfile);

    TestProfile save(List<Request> requests);
}
