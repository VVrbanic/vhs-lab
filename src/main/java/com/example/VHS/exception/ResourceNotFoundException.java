package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ResourceNotFoundException(String message) {
        super(message);
        logger.warn(message);

    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }
}
