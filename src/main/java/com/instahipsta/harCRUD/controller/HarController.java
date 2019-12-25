package com.instahipsta.harCRUD.controller;

import com.instahipsta.harCRUD.domain.Har;
import com.instahipsta.harCRUD.service.FileService;
import com.instahipsta.harCRUD.service.HarService;
import com.instahipsta.harCRUD.service.TestProfileService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;


@RestController
@RequestMapping("/har")
public class HarController {

    private FileService fileService;
    private HarService harService;
    private TestProfileService testProfileService;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public HarController(FileService fileService, HarService harService,
                         TestProfileService testProfileService, RabbitTemplate rabbitTemplate) {
        this.fileService = fileService;
        this.harService = harService;
        this.testProfileService = testProfileService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public HarController() {}

    @Transactional
    @PostMapping("upload")
    public String uploadHar(@RequestParam MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
        Har savedHar = null;

        String resultFileName = fileService.saveFile(file);

        if (resultFileName != null) {
            Har har = harService.createHarFromFile(resultFileName);
            savedHar = harService.save(har);
        }

        if (resultFileName != null && savedHar != null) {
            testProfileService.harToTestProfile(data);
            rabbitTemplate.convertAndSend("har", data);
        }

        return Objects.requireNonNull(savedHar).toString();
    }
}