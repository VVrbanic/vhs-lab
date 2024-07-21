package com.example.VHS.exception;
import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VhsesNotFoundException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public VhsesNotFoundException(String message) {
        super(message);
    }

    public VhsesNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error("VHSes are not found!");

    }

}
