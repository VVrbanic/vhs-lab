package com.example.VHS.controller;

import com.example.VHS.entity.Rental;
import com.example.VHS.entity.RentalValidation;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.RentalException;
import com.example.VHS.exception.RentalReturnedException;
import com.example.VHS.service.RentalService;
import com.example.VHS.service.UserService;
import com.example.VHS.service.VhsService;
import jakarta.validation.Valid;
import org.hibernate.dialect.MyISAMStorageEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final RentalService rentalService;
    private final VhsService vhsService;
    private final UserService userService;

    public RentalController(RentalService rentalService, VhsService vhsService, UserService userService) {
        this.rentalService = rentalService;
        this.vhsService = vhsService;
        this.userService = userService;
    }

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentRental(@Valid @RequestBody RentalValidation rentalNew){
        ResponseEntity<Rental> createRental = rentalService.create(rentalNew);
        return createRental;
    }

    @PutMapping("/return")
    public ResponseEntity<Rental> returnRental(@RequestParam Integer id) {
        ResponseEntity<Rental> response = rentalService.returnRental(id);
        return response;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({RentalException.class})
    public Map<String, String> handleValidationException(RentalException ex){
        Map<String ,String> errors = new HashMap<>();
        errors.put("coflict", ex.getMessage());
        return errors;
    }

}
