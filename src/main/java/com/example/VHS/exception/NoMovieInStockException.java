package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoMovieInStockException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public NoMovieInStockException(String message) {
        super(message);
    }

    public NoMovieInStockException(String message, Throwable cause) {
        super(message, cause);
        logger.error("This film is out of stock!");

    }
}
