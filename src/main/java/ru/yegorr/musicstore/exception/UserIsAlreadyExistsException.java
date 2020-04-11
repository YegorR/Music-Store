package ru.yegorr.musicstore.exception;

public class UserIsAlreadyExistsException extends ApplicationException {
    public UserIsAlreadyExistsException(String msg) {
        super(msg);
    }
}
