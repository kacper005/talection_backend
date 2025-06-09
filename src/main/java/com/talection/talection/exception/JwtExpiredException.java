package com.talection.talection.exception;

public class JwtExpiredException extends RuntimeException
{
    /**
     * Constructs a new JwtExpiredException with the specified detail message.
     *
     * @param message the detail message
     */
    public JwtExpiredException(String message) {
        super(message);
    }
}
