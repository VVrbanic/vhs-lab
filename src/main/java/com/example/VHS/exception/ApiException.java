package com.example.VHS.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamps;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamps) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamps = timestamps;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamps() {
        return timestamps;
    }
}



