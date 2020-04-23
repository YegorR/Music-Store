package ru.yegorr.musicstore.exception;

public class ApplicationException extends Exception {
    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
