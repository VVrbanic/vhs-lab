package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class rentalException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public rentalException(String message) {
        super(message);
        logger.warn(message);
        System.out.println(message);

    }

    public rentalException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);
        System.out.println(message);

    }
}
