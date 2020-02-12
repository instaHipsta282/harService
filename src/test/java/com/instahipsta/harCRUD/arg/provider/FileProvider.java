package com.instahipsta.harCRUD.arg.provider;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProvider {

    public static MultipartFile getMultipartFile() throws IOException {
        String name = "file";
        String contentType = "application/json";
        String originalFileName = "test_archive.har";
        Path path = Paths.get("filesForTests/test_archive.har");
        byte[] content = Files.readAllBytes(path);

        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    public static MultipartFile getNotValidMultipartFile() throws IOException {
        String name = "file";
        String contentType = "application/json";
        String originalFileName = "test_archive.har";
        Path path = Paths.get("filesForTests/test4.json");
        byte[] content = Files.readAllBytes(path);

        return new MockMultipartFile(name, originalFileName, contentType, content);
    }
}
