package com.instahipsta.harCRUD.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.harCRUD.domain.Har;
import com.instahipsta.harCRUD.service.FileService;
import com.instahipsta.harCRUD.service.HarService;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/har")
public class HarController {

    @Autowired
    private ObjectMapper objectMapper;
    private FileService fileService;
    private HarService harService;
    private RabbitTemplate rabbitTemplate;
    @Value("${file.downloads}")
    private String downloadPath;

    @Autowired
    public HarController(FileService fileService, HarService harService, RabbitTemplate rabbitTemplate) {
        this.fileService = fileService;
        this.harService = harService;
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
            File savedFile = new File(downloadPath + "/" + resultFileName);
            Har har = harService.createHarFromFile(savedFile.toPath());
            savedHar = harService.save(har);
        }

        if (resultFileName != null && savedHar != null) {
            rabbitTemplate.convertAndSend("har", data);
        }
        return objectMapper.writeValueAsString(savedHar);
    }
}
