package com.talection.talection.exception;

public class InvalidChoiceException extends RuntimeException {
    public InvalidChoiceException(String message) {
        super(message);
    }

    public InvalidChoiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
