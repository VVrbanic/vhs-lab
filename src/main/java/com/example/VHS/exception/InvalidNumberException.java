package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidNumberException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public InvalidNumberException(String message) {
        super(message);
        logger.warn(message);
    }

    public InvalidNumberException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }
}
