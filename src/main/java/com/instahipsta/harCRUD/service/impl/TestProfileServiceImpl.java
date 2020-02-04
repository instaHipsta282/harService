package com.instahipsta.harCRUD.service.impl;

import com.instahipsta.harCRUD.model.entity.Request;
import com.instahipsta.harCRUD.model.entity.TestProfile;
import com.instahipsta.harCRUD.repository.TestProfileRepo;
import com.instahipsta.harCRUD.service.TestProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TestProfileServiceImpl implements TestProfileService {

    private TestProfileRepo testProfileRepo;

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
        requests.forEach(r -> r.setTestProfile(testProfile));
        testProfile.setRequests(requests);
        testProfile.setRequestsCount(requests.size());
        return testProfile;
    }
}
