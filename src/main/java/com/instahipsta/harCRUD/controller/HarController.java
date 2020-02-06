package com.instahipsta.harCRUD.controller;

import com.instahipsta.harCRUD.model.dto.HarDto;
import com.instahipsta.harCRUD.service.HarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/har")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class HarController {

    private final HarService harService;

    @PutMapping("{id}")
    public ResponseEntity<HarDto> updateHar(@RequestBody HarDto har,
                                            @PathVariable long id) {
        return harService.update(har, id);
    }

    @GetMapping("{id}")
    public ResponseEntity<HarDto> getHar(@PathVariable long id) {
        return harService.find(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<HarDto> deleteHar(@PathVariable long id) {

        harService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<HarDto> uploadHar(@RequestParam MultipartFile file) {


        return harService.add(file);
    }
}
