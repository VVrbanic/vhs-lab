package com.example.VHS.controller;

import com.example.VHS.entity.Price;
import com.example.VHS.exception.rentalException;
import com.example.VHS.service.PriceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

    @Autowired
    private PriceService priceService;

    @PostMapping("/add")
    public ResponseEntity<Price> addNewPrice(@Valid @RequestBody Price price){
        try{
            //add new price
            LocalDateTime time = LocalDateTime.now();
            price.setActive(Boolean.TRUE);
            price.setDate_from(time);
            Price newPrice = priceService.save(price, time);
            return new ResponseEntity<>(newPrice, HttpStatus.CREATED);
        } catch (RuntimeException e) {

            logger.error("Error while processing price request", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
