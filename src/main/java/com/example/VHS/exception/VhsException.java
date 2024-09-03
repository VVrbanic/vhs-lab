package com.example.VHS.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class VhsException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(VhsException.class);

    public VhsException(String message) {
        super(message);
        logger.warn(message);

    }

    public VhsException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }
}
