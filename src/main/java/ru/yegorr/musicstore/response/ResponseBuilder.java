package ru.yegorr.musicstore.response;

import org.springframework.http.HttpStatus;
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

    public ResponseBuilder code(HttpStatus status) {
        response.setCode(status.value());
        return this;
    }

    public ResponseEntity<?> getResponseEntity() {
        return ResponseEntity.status(response.getCode()).body(response);
    }
}


class ResponseDto {
    private int code = 200;

    private Object body;

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
}
