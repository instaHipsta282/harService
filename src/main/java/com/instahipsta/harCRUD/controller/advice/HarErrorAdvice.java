package com.instahipsta.harCRUD.controller.advice;

import com.instahipsta.harCRUD.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class HarErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<CustomException> handleRunTimeException(RuntimeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(
                new CustomException("Something went wrong"), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CustomException> handleHarNotFoundException() {
        return new ResponseEntity<>(
                new CustomException("There is no such har"), NOT_FOUND);
    }


    @Data
    @AllArgsConstructor
    private static class CustomException {
        private String message;
    }
}
