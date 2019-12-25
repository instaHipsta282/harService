package com.instahipsta.harCRUD.impl;

import com.instahipsta.harCRUD.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.downloads}")
    private String downloadPath;

    @Override
    public String saveFile(MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File downloadDir = new File(downloadPath);

            if (!downloadDir.exists()) downloadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try { file.transferTo(new File(downloadDir.getAbsolutePath() + "/" + resultFilename)); }
            catch (IOException e) { e.printStackTrace(); }

            return resultFilename;
        }
        return null;
    }


}
