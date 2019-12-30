package com.instahipsta.harCRUD.service;

import com.instahipsta.harCRUD.domain.Har;
import org.springframework.web.multipart.MultipartFile;

public interface HarService {
    Har save(Har har);

    Har findById(Long id);

    Har create(String version, String browser, String browserVersion, String resultFileName);

    Har createHarFromFile(String resultFileName);
}
