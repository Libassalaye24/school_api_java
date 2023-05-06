package com.school.school.exception;

public class InscriptionNotFoundException extends InscriptionException {
    public InscriptionNotFoundException(String message) {
        super(message);
    }

    public InscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
