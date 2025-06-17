package com.talection.talection.exception;

public class InvalidTestSessionDataException extends RuntimeException {
    public InvalidTestSessionDataException(String message) {
        super(message);
    }

    public InvalidTestSessionDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
