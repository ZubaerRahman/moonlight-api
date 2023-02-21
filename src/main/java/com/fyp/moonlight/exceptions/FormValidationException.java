package com.fyp.moonlight.exceptions;

import java.util.List;

public class FormValidationException extends Exception{

    private List<String> errors;

    public FormValidationException(String message) {
        super(message);
    }

    public FormValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

}
