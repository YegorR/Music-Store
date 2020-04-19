package ru.yegorr.musicstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ClientException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleApplicationException(Exception ex) {
        int code = 500;
        String error = "Something wrong on the server";

        if (ex instanceof ClientException) {
            log.debug(ex.toString());
            code = 400;
            error = ex.getMessage();
        } else {
            log.error(ex.toString());
            ex.printStackTrace();
        }

        return ResponseBuilder.getBuilder().code(code).error(error).getResponseEntity();
    }
}
