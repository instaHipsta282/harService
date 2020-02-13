package com.instahipsta.harCRUD.arg.provider;

import javassist.bytecode.stackmap.TypeData;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.vladmihalcea.hibernate.type.util.ClassLoaderUtils.getClassLoader;

public class FileProvider {

    public static MultipartFile getMultipartFile() throws IOException {

        try (InputStream inputStream = TypeData.ClassName.class
                .getClassLoader().getResourceAsStream("data/test_archive.har")) {

            return new MockMultipartFile("file", inputStream);
        }
    }

    public static MultipartFile getNotValidMultipartFile() throws IOException {

        try (InputStream inputStream = TypeData.ClassName.class
                .getClassLoader().getResourceAsStream("data/test4.json")) {

            return new MockMultipartFile("file", inputStream);
        }
    }
}
