package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestIncorretException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public RequestIncorretException(String message) {
        super(message);
    }

    public RequestIncorretException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message);

    }
}
