package com.instahipsta.harCRUD.arg;

import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Stream;

import static com.instahipsta.harCRUD.arg.provider.RequestProvider.getRequest;
import static com.instahipsta.harCRUD.arg.provider.TestProfileProvider.getEmptyTestProfile;
import static com.instahipsta.harCRUD.arg.provider.TestProfileProvider.getTestProfileWithRequests;

public class TestProfileArgs {

    static Stream<Arguments> testProfileSource() throws IOException {
        return Stream.of(Arguments.of(
                getTestProfileWithRequests()));
    }

    static Stream<Arguments> requestsSource() throws IOException {
        return Stream.of(Arguments.of(
                Collections.singletonList(getRequest())));
    }

    static Stream<Arguments> testProfileWithoutRequestsSource() {
        return Stream.of(Arguments.of(
                getEmptyTestProfile()));
    }

}
