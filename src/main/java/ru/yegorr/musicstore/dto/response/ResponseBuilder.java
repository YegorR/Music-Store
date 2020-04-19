package ru.yegorr.musicstore.dto.response;

import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private ResponseDto response;

    public static ResponseBuilder getBuilder() {
        return new ResponseBuilder();
    }

    public ResponseBuilder body(Object body) {
        response.setBody(body);
        return this;
    }

    public ResponseBuilder code(int code) {
        response.setCode(code);
        return this;
    }

    public ResponseBuilder error(String error) {
        response.setError(error);
        return this;
    }

    public ResponseEntity<?> getResponseEntity() {
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
