package com.talection.talection.exception;


public class TestOptionNotFoundException extends RuntimeException {
    public TestOptionNotFoundException(String message) {
        super(message);
    }

    public TestOptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
