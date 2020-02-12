package com.instahipsta.harCRUD.arg;

import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.util.stream.Stream;

import static com.instahipsta.harCRUD.arg.provider.RequestProvider.getRequest;
import static com.instahipsta.harCRUD.arg.provider.TestProfileProvider.getEmptyTestProfile;
import static com.instahipsta.harCRUD.arg.provider.TestProfileProvider.getTestProfileWithRequests;
import static java.util.Arrays.asList;

public class TestProfileArgs {

    static Stream<Arguments> testProfileSource() throws IOException {
        return Stream.of(Arguments.of(
                getTestProfileWithRequests()));
    }

    static Stream<Arguments> requestsSource() throws IOException {
        return Stream.of(Arguments.of(
                asList(getRequest())));
    }

    static Stream<Arguments> testProfileWithoutRequestsSource() throws IOException {
        return Stream.of(Arguments.of(
                getEmptyTestProfile()));
    }

}
