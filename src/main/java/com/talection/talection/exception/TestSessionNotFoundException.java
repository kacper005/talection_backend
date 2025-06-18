package com.talection.talection.exception;

public class TestSessionNotFoundException extends RuntimeException {
    public TestSessionNotFoundException(String message) {
        super(message);
    }

    public TestSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
