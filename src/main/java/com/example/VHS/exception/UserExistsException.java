package com.example.VHS.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExistsException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(UserExistsException.class);

    public UserExistsException(String message) {
        super(message);
        logger.warn(message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
        logger.warn(message);

    }


}
