package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoDueException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(NoDueException.class);

    public NoDueException(String message) {
        super(message);
        logger.warn(message);
    }

    public NoDueException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);
    }
}
