package com.example.VHS.controller;

import com.example.VHS.entity.Rental;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.IdNotValidException;
import com.example.VHS.exception.RentIdException;
import com.example.VHS.exception.RentalException;
import com.example.VHS.exception.RentalReturnedException;
import com.example.VHS.service.RentalService;
import com.example.VHS.service.UserService;
import com.example.VHS.service.VhsService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Rental> rentRental(@Valid @RequestBody Rental rentalNew){
        Integer vhsId = rentalNew.getVhs().getId();
        Integer userId = rentalNew.getUser().getId();
        // Fetch VHS and User by ID
        Vhs vhs = vhsService.getVhsById(vhsId);
        User user = userService.getUserById(userId);

        //check if the film is avaliable
        if (vhs.getNumberInStock() <= 0){
            throw new RentalException("No movie in stock!");
        }

        Rental rental = new Rental();
        rental.setVhs(vhs);
        rental.setUser(user);
        rental.setRentedDate(LocalDateTime.now());
        rental.setDueDate(LocalDateTime.now().plusDays(7)); //7 days from now
        rental.setReturnDate(null);

        Rental savedRental = rentalService.rentRental(rental);
        return new ResponseEntity<>(savedRental, HttpStatus.CREATED);
    }

    @PutMapping("/return")
    public ResponseEntity<Rental> returnRental(@RequestParam Integer id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental.getReturnDate() != null) {
            throw new RentalReturnedException("This rental has already been returned!");
        }
        //test
        rental.setReturnDate(LocalDateTime.now().plusDays(10));
        //rental.setReturnDate(LocalDateTime.now());
        Rental returnRental = rentalService.returnRental(rental);
        return new ResponseEntity<>(returnRental, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
