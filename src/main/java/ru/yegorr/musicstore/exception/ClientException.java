package ru.yegorr.musicstore.exception;


import org.springframework.http.HttpStatus;

public class ClientException extends ServerException {
    public ClientException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public ClientException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }

    public ClientException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, cause);
    }

    public ClientException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public ClientException(String msg, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, msg, cause);
    }

    public ClientException(HttpStatus httpStatus, String msg) {
        super(httpStatus, msg);
    }

    public ClientException(HttpStatus httpStatus, Throwable cause) {
        super(httpStatus, cause);
    }

    public ClientException(HttpStatus httpStatus, String msg, Throwable cause) {
        super(httpStatus, msg, cause);
    }
}
