package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RentIdException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public RentIdException(String message) {
        super(message);
        logger.warn(message);
    }

    public RentIdException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }


}
