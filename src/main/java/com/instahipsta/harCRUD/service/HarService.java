package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.domain.Har;

import java.nio.file.Path;

public interface HarService {
    Har save(Har har);

    Har findById(Long id);

    Har create(String version, String browser, String browserVersion, String resultFileName);

    Har createHarFromFile(Path filePath);
}
