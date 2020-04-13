package ru.yegorr.musicstore.exception;

public class ResourceIsNotFoundException extends ClientException {
    public ResourceIsNotFoundException(String msg) {
        super(msg);
    }
}