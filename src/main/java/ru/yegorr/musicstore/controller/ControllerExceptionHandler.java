package ru.yegorr.musicstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<?> handleServerException(ServerException exception) {
        log.error(exception.getMessage(), exception);
        return sendResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleClientException(ClientException exception) {
        return sendResponse(exception.getHttpStatus(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return handleServerException(new ServerException(exception));
    }


    private ResponseEntity<?> sendResponse(HttpStatus httpStatus, String errorMessage) {
        return ResponseBuilder.getBuilder().code(httpStatus.value()).error(errorMessage).getResponseEntity();
    }
}
