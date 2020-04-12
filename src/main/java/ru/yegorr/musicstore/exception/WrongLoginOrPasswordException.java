package ru.yegorr.musicstore.exception;

public class WrongLoginOrPasswordException extends ClientException {
    public WrongLoginOrPasswordException(String msg) {
        super(msg);
    }
}
