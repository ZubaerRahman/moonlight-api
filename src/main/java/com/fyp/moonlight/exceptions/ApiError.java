package com.fyp.moonlight.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private String message;
    private Instant timestamp;
    private List<String> subErrors;

    public ApiError(HttpStatus status) {
        this.status = status;
        this.timestamp = Instant.now();
    }

    public ApiError(HttpStatus status, String message, List<String> subErrors) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
        this.subErrors = subErrors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<String> subErrors) {
        this.subErrors = subErrors;
    }

}
