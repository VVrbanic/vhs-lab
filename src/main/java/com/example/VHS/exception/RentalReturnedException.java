package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RentalReturnedException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RentalReturnedException.class);

    public RentalReturnedException(String message) {
        super(message);
        logger.warn(message);
    }

    public RentalReturnedException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);
    }
}
