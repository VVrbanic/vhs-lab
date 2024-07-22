package com.example.VHS.controller;

import com.example.VHS.entity.Rental;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.RentalException;
import com.example.VHS.service.RentalService;
import com.example.VHS.service.UserService;
import com.example.VHS.service.VhsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RentalService rentalService;
    @Autowired
    private VhsService vhsService;
    @Autowired
    private UserService userService;

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentRental(@Valid @RequestBody Rental rentalNew){
        try {
            Integer vhsId = rentalNew.getVhs().getId();
            Integer userId = rentalNew.getUser().getId();
            // Fetch VHS and User by ID
            Vhs vhs = vhsService.getVhsById(vhsId);
            //check if the film is avaliable
            if (vhs.getNumberInStock() <= 0){
                throw new RentalException("No movie in stock!");
            }
            User user = userService.getUserById(userId);
            Rental rental = new Rental();
            rental.setVhs(vhs);
            rental.setUser(user);
            rental.setRentedDate(LocalDateTime.now());
            rental.setDueDate(LocalDateTime.now().plusDays(7)); //7 days from now
            rental.setReturnDate(null);

            Rental savedRental = rentalService.rentRental(rental);
            return new ResponseEntity<>(savedRental, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            logger.error("Error while processing rental request", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/return")
    public ResponseEntity<Rental> returnRental(@RequestParam Integer id) {
        try {

            Rental rental = rentalService.getRentalById(id);
            if(rental.getReturnDate() != null){
                throw new RentalException("This rental has already been returned!");
            }
            rental.setReturnDate(LocalDateTime.now().plusDays(10));
            Rental retunRental = rentalService.returnRental(rental);
            return new ResponseEntity<>(retunRental, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            logger.error("Error while processing rental request, the ID is not valid");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/payDue")
    public ResponseEntity<Rental> payDue(@RequestParam Integer id, @RequestParam Float payment){
        try {
            Rental rental = rentalService.getRentalById(id);
            Float currentDue = rental.getUnpaidDue();
            if(currentDue == 0){
                throw new RuntimeException("The due is already paid!");
            }else if(currentDue > payment){
                throw new RuntimeException("This is too much money, do you wanna leave us a tip?");
            }else{
                Float newDue = rental.getUnpaidDue() - currentDue;
                rental.setUnpaidDue(newDue);
            }
            if(rental.getReturnDate() != null){
                throw new RentalException("This rental has already been returned!");
            }
            rental.setReturnDate(LocalDateTime.now().plusDays(10));
            Rental returnRental = rentalService.returnRental(rental);
            return new ResponseEntity<>(returnRental, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            logger.error("Error while processing rental request, the ID is not valid");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

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
