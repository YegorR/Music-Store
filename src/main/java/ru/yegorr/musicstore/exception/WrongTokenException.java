package ru.yegorr.musicstore.exception;

public class WrongTokenException extends ClientException {
    public WrongTokenException (String msg) {
        super(msg);
    }
}
