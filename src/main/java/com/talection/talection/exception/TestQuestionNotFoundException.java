package com.talection.talection.exception;

public class TestQuestionNotFoundException extends RuntimeException {
    public TestQuestionNotFoundException(String message) {
        super(message);
    }

    public TestQuestionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
