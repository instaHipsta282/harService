package com.instahipsta.harCRUD.service.impl;

import com.instahipsta.harCRUD.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${file.downloads}")
    private String downloadPath;

    @Override
    public String saveFile(MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File downloadDir = new File(downloadPath);

            if (!downloadDir.exists()) downloadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(downloadDir.getAbsolutePath() + "/" + resultFilename));
                return resultFilename;
            }
            catch (IOException e) { logger.error("Failed to transfer multipart file", e); }
        }
        return null;
    }


}
