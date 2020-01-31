package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.model.entity.Har;
import com.instahipsta.harCRUD.service.FileService;
import com.instahipsta.harCRUD.service.HarService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/har")
@RequiredArgsConstructor
public class HarController {

    @NonNull
    private ObjectMapper objectMapper;
    @NonNull
    private FileService fileService;
    @NonNull
    private HarService harService;

    @PostMapping("upload")
    public String uploadHar(@RequestParam MultipartFile file) throws IOException {
        Har har = harService.createHarFromFile(file);
        Har savedHar = harService.save(har);

        if (savedHar != null) {
            harService.sendHarInQueue(savedHar.getContent());
        }
        return objectMapper.writeValueAsString(harService.harToDto(savedHar));
    }
}
