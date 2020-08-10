package ru.yegorr.musicstore.exception;

import org.springframework.http.HttpStatus;

public class ServerException extends Exception {
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public ServerException(String msg) {
        super(msg);
    }

    public ServerException(Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), cause);
    }

    public ServerException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.httpStatus = httpStatus;
    }

    public ServerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ServerException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public ServerException(HttpStatus httpStatus, Throwable cause) {
        super(httpStatus.getReasonPhrase(), cause);
        this.httpStatus = httpStatus;
    }

    public ServerException(HttpStatus httpStatus, String msg, Throwable cause) {
        super(msg, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
