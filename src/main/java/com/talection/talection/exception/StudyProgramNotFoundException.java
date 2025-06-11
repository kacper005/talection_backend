package com.talection.talection.exception;

public class StudyProgramNotFoundException extends RuntimeException {
    public StudyProgramNotFoundException(String message) {
        super(message);
    }

    public StudyProgramNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
