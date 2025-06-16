package com.talection.talection.exception;

public class LikertScaleOutOfBoundsException extends RuntimeException {
    public LikertScaleOutOfBoundsException(String message) {
        super(message);
    }

    public LikertScaleOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
