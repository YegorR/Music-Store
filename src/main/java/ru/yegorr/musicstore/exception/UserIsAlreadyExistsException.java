package ru.yegorr.musicstore.exception;

public class UserIsAlreadyExistsException extends ClientException {
    public UserIsAlreadyExistsException(String msg) {
        super(msg);
    }
}
