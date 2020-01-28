package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.dto.HarDTO;
import com.instahipsta.harCRUD.entity.Har;

import java.nio.file.Path;

public interface HarService {
    HarDTO save(Har har);

    Har findById(Long id);

    Har create(String version, String browser, String browserVersion, String resultFileName);

    Har createHarFromFile(Path filePath);
}
