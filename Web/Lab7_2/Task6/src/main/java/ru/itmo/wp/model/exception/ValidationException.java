package ru.itmo.wp.model.exception;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String message, Exception ex) {
        super(message, ex);
    }
}
