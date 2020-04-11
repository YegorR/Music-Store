package ru.yegorr.musicstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yegorr.musicstore.dto.ResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleApplicationException(Exception ex) {
        ResponseDto response = new ResponseDto();
        response.setCode(500);
        response.setError("Something wrong on the server");
        return ResponseEntity.status(500).body(response);
    }
}
