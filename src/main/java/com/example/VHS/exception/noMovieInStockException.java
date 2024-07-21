package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import com.example.VHS.entities.VHS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class noMovieInStockException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    public noMovieInStockException(String message) {
        super(message);
    }

    public noMovieInStockException(String message, Throwable cause) {
        super(message, cause);
        logger.error("This film is out of stock!");

    }
}
