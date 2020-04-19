package ru.yegorr.musicstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yegorr.musicstore.dto.response.ResponseDto;
import ru.yegorr.musicstore.exception.ClientException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleApplicationException(Exception ex) {
        ResponseDto response = new ResponseDto();
        if (ex instanceof ClientException) {
            log.debug(ex.toString());
            response.setCode(400);
            response.setError(ex.getMessage());
        } else {
            log.error(ex.toString());
            ex.printStackTrace();
            response.setCode(500);
            response.setError("Something wrong on the server");
        }
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
