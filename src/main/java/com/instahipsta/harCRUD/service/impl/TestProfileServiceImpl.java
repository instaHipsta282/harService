package com.instahipsta.harCRUD.service.impl;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TestProfileServiceImpl implements TestProfileService {

    private TestProfileRepo testProfileRepo;

    public TestProfileServiceImpl(TestProfileRepo testProfileRepo) {
        this.testProfileRepo = testProfileRepo;
    }


    @Override
    public TestProfile save(TestProfile testProfile) {

        if (testProfile.getRequests() == null) testProfile.setRequests(new ArrayList<>());

        testProfile.setRequestsCount(testProfile.getRequests().size());
        testProfile.getRequests().forEach(r -> r.setTestProfile(testProfile));

        return testProfileRepo.save(testProfile);
    }

    @Override
    public TestProfile save(List<Request> requests) {
        TestProfile testProfile = TestProfile.builder().build();

        testProfile.setRequests(requests);
        testProfile.setRequestsCount(requests.size());
        testProfile.getRequests().forEach(r -> r.setTestProfile(testProfile));

        return testProfileRepo.save(testProfile);
    }
}
