package com.PUMB.test.exceptionHandler.exception;

public class MissingFileException extends RuntimeException {

    public MissingFileException(String message) {
        super(message);
    }
}