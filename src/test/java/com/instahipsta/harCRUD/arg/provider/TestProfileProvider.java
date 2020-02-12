package com.instahipsta.harCRUD.arg.provider;

import com.instahipsta.harCRUD.model.entity.TestProfile;

import java.io.IOException;
import java.util.Arrays;

public class TestProfileProvider {

    public static TestProfile getTestProfileWithRequests() throws IOException {
        TestProfile testProfile = new TestProfile();
        testProfile.setRequests(Arrays.asList(RequestProvider.getRequest()));
        return testProfile;
    }

    public static TestProfile getEmptyTestProfile() throws IOException {
        return new TestProfile();
    }
}
