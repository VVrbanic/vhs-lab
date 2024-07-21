package com.example.VHS.controller;

import com.example.VHS.entity.Rental;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.DuplicateReturnRentalException;
import com.example.VHS.exception.DuplicateVhsException;
import com.example.VHS.exception.NoMovieInStockException;
import com.example.VHS.exception.VhsesNotFoundException;
import com.example.VHS.service.RentalService;
import com.example.VHS.service.UserService;
import com.example.VHS.service.VhsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public ResponseEntity<Rental> rentRental(@RequestParam Integer vhsId, @RequestParam Integer userId){
        try {
            // Fetch VHS and User by ID
            Vhs vhs = vhsService.getVhsById(vhsId);
            //check if the film is avaliable
            if (vhs.getNumberInStock() <= 0){
                throw new NoMovieInStockException("No movie in stock!");
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

            logger.error("Error while processing rental request: vhsId={}, userId={}", vhsId, userId, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/return")
    public ResponseEntity<Rental> returnRental(@RequestParam Integer id) {
        try {

            Rental rental = rentalService.getRentalById(id);
            if(rental.getReturnDate() != null){
                throw new DuplicateReturnRentalException("This rental has already been returned!");
            }
            rental.setReturnDate(LocalDateTime.now().plusDays(10));
            Rental retunRental = rentalService.returnRental(rental);
            return new ResponseEntity<>(retunRental, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            logger.error("Error while processing rental request, the ID is not valid", id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
