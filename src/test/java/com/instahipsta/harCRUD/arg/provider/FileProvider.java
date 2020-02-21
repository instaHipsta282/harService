package com.instahipsta.harCRUD.arg.provider;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FileProvider {

    public static MultipartFile getMultipartFile(String filename) throws IOException {

        try (InputStream inputStream = new ClassPathResource("data/" + filename).getInputStream()) {
            return new MockMultipartFile("file", inputStream);
        }
    }
}
