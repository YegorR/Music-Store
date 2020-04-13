package ru.yegorr.musicstore.exception;

public class ForbiddenException extends ClientException {
    public ForbiddenException(String msg) {
        super(msg);
    }
}
