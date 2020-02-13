package com.instahipsta.harCRUD.arg;

import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.util.stream.Stream;

import static com.instahipsta.harCRUD.arg.provider.FileProvider.getMultipartFile;
import static com.instahipsta.harCRUD.arg.provider.FileProvider.getNotValidMultipartFile;
import static com.instahipsta.harCRUD.arg.provider.HARDtoProvider.getHARDto;
import static com.instahipsta.harCRUD.arg.provider.HarProvider.getHAR;

public class HARArgs {

    static Stream<Arguments> fileHarAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile(),
                getHAR("test_archive.har"),
                1L));
    }

    static Stream<Arguments> fileAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile(),
                1L));
    }

    static Stream<Arguments> notValidFileAndIdSource() throws IOException {
        return Stream.of(Arguments.of(
                getNotValidMultipartFile(),
                1L));
    }

    static Stream<Arguments> fileSource() throws IOException {
        return Stream.of(Arguments.of(
                getMultipartFile()));
    }

    static Stream<Arguments> notValidFileSource() throws IOException {
        return Stream.of(Arguments.of(
                getNotValidMultipartFile()));
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
}
