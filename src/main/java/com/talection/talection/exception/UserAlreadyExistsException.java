package com.talection.talection.exception;

public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
