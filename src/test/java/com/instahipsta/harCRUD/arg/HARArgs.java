package com.instahipsta.harCRUD.arg;

import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Stream;

import static com.instahipsta.harCRUD.arg.provider.FileProvider.getMultipartFile;
import static com.instahipsta.harCRUD.arg.provider.HARDtoProvider.getHARDto;
import static com.instahipsta.harCRUD.arg.provider.HarProvider.getHAR;
import static com.instahipsta.harCRUD.arg.provider.RequestProvider.getRequest;

public class HARArgs {

    static Stream<Arguments> fileHarAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test_archive.har"),
                getHAR("test_archive.har"),
                1L));
    }

    static Stream<Arguments> newFileHarAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test_archive_upd.har"),
                getHAR("test_archive.har"),
                1L));
    }

    static Stream<Arguments> fileAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test_archive.har"),
                1L));
    }

    static Stream<Arguments> notValidFileAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test4.json"),
                1L));
    }

    static Stream<Arguments> fileSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test_archive.har")));
    }

    static Stream<Arguments> notValidFileSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test4.json")));
    }

    static Stream<Arguments> jsonMappingExFileSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("test5.json")));
    }

    static Stream<Arguments> jsonProcessingExFileSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile("image.webp")));
    }

    static Stream<Arguments> harAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getHAR("test_archive.har"),
                1L));
    }

    static Stream<Arguments> harSource() throws IOException {
        return Stream.of(Arguments.of(
                getHAR("test_archive.har")));
    }

    static Stream<Arguments> invalidHarSource() throws IOException {
        return Stream.of(Arguments.of(
                getHAR("test5.json")));
    }

    static Stream<Arguments> harDtoHarAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getHARDto(),
                getHAR("test_archive.har"),
                1L));
    }

    static Stream<Arguments> harDtoAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getHARDto(),
                1L));
    }

    static Stream<Arguments> harDtoSource() throws IOException {
        return Stream.of(Arguments.of(
                getHARDto()));
    }

    static Stream<Arguments> harDtoAndRequestsSource() throws IOException {
        return Stream.of(Arguments.of(
                getHARDto(),
                Collections.singletonList(getRequest())));
    }
}
