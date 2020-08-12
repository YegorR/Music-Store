package ru.yegorr.musicstore.exception;

public class SuddenException extends RuntimeException {
  public SuddenException(String message) {
    super(message);
  }

  public SuddenException(String message, Throwable cause) {
    super(message, cause);
  }
}
