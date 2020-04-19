package ru.yegorr.musicstore.dto.response;

import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private final ResponseDto response = new ResponseDto();

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

class ResponseDto {
    private int code;

    private Object body;

    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
