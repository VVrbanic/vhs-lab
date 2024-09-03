package com.example.VHS.exception;

import com.example.VHS.controller.RentalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceForTomorrowAdded extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(PriceForTomorrowAdded.class);

    public PriceForTomorrowAdded(String message) {
        super(message);
        logger.warn(message);

    }

    public PriceForTomorrowAdded(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }
}
