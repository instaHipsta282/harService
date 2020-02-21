package com.instahipsta.harCRUD.arg.provider;

import com.instahipsta.harCRUD.model.entity.TestProfile;

import java.io.IOException;
import java.util.Collections;

public class TestProfileProvider {

    public static TestProfile getTestProfileWithRequests() throws IOException {
        return TestProfile.builder()
                .requests(Collections.singletonList(RequestProvider.getRequest()))
                .build();
    }

    public static TestProfile getEmptyTestProfile() {
        return TestProfile.builder().build();
    }
}
