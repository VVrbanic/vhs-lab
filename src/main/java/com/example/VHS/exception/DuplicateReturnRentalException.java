package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuplicateReturnRentalException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public DuplicateReturnRentalException(String message) {
        super(message);
    }

    public DuplicateReturnRentalException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message);

    }
}
