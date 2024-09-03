package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RentalException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public RentalException(String message) {
        super(message);
        logger.warn(message);

    }

    public RentalException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }
}
