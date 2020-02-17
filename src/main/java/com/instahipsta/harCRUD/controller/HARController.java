package com.instahipsta.harCRUD.controller;

import com.instahipsta.harCRUD.model.dto.HAR.HARDto;
import com.instahipsta.harCRUD.model.exception.JsonValidateFailedException;
import com.instahipsta.harCRUD.model.exception.ResourceNotFoundException;
import com.instahipsta.harCRUD.model.exception.dto.CustomException;
import com.instahipsta.harCRUD.service.HarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.instahipsta.harCRUD.model.exception.ExceptionMessage.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/har")
@Slf4j
public class HARController {

    private HarService harService;

    public HARController(HarService harService) {
        this.harService = harService;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateHar(@Valid @RequestBody HARDto dto,
                                       @PathVariable long id) {
        try {
            return harService.update(dto, id);
        }
        catch (ResourceNotFoundException ex) {
            log.warn("Har with id {} not found", id);
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(CustomException.builder()
                            .message(RESOURCE_NOT_FOUND_EXCEPTION.getErrorMessage() + id)
                            .build());
        }
        catch (Exception ex) {
            log.error("Exception in updateHar method, har id is {}, error: {}", id, ex);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(CustomException.builder()
                            .message(INTERNAL_SERVER_ERROR_C.getErrorMessage())
                            .build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getHar(@PathVariable long id) {
        try {
            return harService.find(id);
        }
        catch (ResourceNotFoundException ex) {
            log.warn("Har with id {} not found", id);
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(CustomException.builder()
                            .message(RESOURCE_NOT_FOUND_EXCEPTION.getErrorMessage() + id)
                            .build());
        }
        catch (Exception ex) {
            log.error("Exception in getHar method, har id is {} error: {}", id, ex);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(CustomException.builder()
                            .message(INTERNAL_SERVER_ERROR_C.getErrorMessage())
                            .build());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteHar(@PathVariable long id) {
        try {
            harService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception ex) {
            log.error("Exception in deleteHar method, har id is {}, error: {}", id, ex);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(CustomException.builder()
                            .message(INTERNAL_SERVER_ERROR_C.getErrorMessage())
                            .build());
        }
    }

    @PostMapping
    public ResponseEntity<?> addHar(@RequestParam MultipartFile file) {
        try {
            return harService.add(file);
        }
        catch(JsonValidateFailedException ex) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(CustomException.builder()
                            .message(JSON_VALIDATE_FAILED_EXCEPTION.getErrorMessage() + ex.getFailList().toString())
                            .build());
        }
        catch (Exception ex) {
            log.error("Exception in addHar method, file {} error: {}", file.getOriginalFilename(), ex);
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(CustomException.builder()
                            .message(INTERNAL_SERVER_ERROR_C.getErrorMessage())
                            .build());
        }
    }
}
