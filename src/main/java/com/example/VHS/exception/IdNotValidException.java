package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdNotValidException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public IdNotValidException(String message) {
        super(message);
        logger.warn(message);
    }

    public IdNotValidException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);
    }
}
