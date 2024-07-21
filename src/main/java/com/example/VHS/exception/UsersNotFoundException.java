package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public UsersNotFoundException(String message) {
        super(message);
    }

    public UsersNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Users not found!");

    }
}

