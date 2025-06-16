package com.talection.talection.exception;

public class TestTemplateNotFoundException extends RuntimeException {
    public TestTemplateNotFoundException(Long id) {
        super("Test template not found with ID: " + id);
    }

    public TestTemplateNotFoundException(String message) {
        super(message);
    }
}
