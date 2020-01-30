package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.model.dto.HarDTO;
import com.instahipsta.harCRUD.model.entity.Har;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public interface HarService {
    HarDTO save(Har har);

    Har findById(Long id);

    Har create(String version,
               String browser,
               String browserVersion,
               String resultFileName);

    Har createHarFromFile(Path filePath);
}
