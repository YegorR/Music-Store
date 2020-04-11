package ru.yegorr.musicstore.exception;

public class UserAlreadyExistsException extends ApplicationException {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
