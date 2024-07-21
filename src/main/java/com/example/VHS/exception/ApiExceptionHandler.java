package com.example.VHS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NoMovieInStockException.class})
    public ResponseEntity<Object> handleNoMovieInStockException(NoMovieInStockException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);

    }
    @ExceptionHandler(value = {UsersNotFoundException.class})
    public ResponseEntity<Object> handleUsersNotFoundException(UsersNotFoundException e){
        //1. Create payload containing exception detail
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);

    }
    @ExceptionHandler(value = {DuplicateReturnRentalException.class})
    public ResponseEntity<Object> handleDuplicateReturnRentalException(DuplicateReturnRentalException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);

    }
}
