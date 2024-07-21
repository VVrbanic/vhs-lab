package com.example.VHS.controller;

import com.example.VHS.entities.Rental;
import com.example.VHS.entities.User;
import com.example.VHS.entities.VHS;
import com.example.VHS.exception.noMovieInStockException;
import com.example.VHS.service.RentalService;
import com.example.VHS.service.UserService;
import com.example.VHS.service.VHSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private VHSService vhsService;

    @Autowired
    private UserService userService;

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentRental(@RequestParam Integer vhsId, @RequestParam Integer userId){
        try {
            // Fetch VHS and User by ID
            VHS vhs = vhsService.getVHSById(vhsId);
            //check if the film is avaliable
            if (vhs.getNumberInStock() <= 0){
                throw new noMovieInStockException("No movie in stock!");
            }

            User user = userService.getUserById(userId);

            Rental rental = new Rental();
            rental.setVhs(vhs);
            rental.setUser(user);
            rental.setRentedDate(LocalDateTime.now());
            rental.setDueDate(LocalDateTime.now().plusDays(7)); //7 days from now
            rental.setReturnDate(null);

            Rental savedRental = rentalService.rentRental(rental);
            //decrease the number of number the films currently avaliable
            return new ResponseEntity<>(savedRental, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            logger.error("Error while processing rental request: vhsId={}, userId={}", vhsId, userId, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
