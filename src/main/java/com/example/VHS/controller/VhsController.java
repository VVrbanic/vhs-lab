package com.example.VHS.controller;


import com.example.VHS.entity.Rental;
import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.InvalidNumberException;
import com.example.VHS.exception.RentalException;
import com.example.VHS.service.VhsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vhses")
public class VhsController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final VhsService vhsService;

    public VhsController(VhsService vhsService) {
        this.vhsService = vhsService;
    }

    @GetMapping
    public List<Vhs> getAllVhses(){
        List<Vhs> vhsList = vhsService.getAllVhs();
        if(vhsList.isEmpty()){
            throw new RentalException("No VHSes found!");
        }
        return vhsList;
    }

    //add new VHS
    @PostMapping("/create")
    public ResponseEntity<Vhs> createVhs(@Valid @RequestBody Vhs vhsNew) {
        String name = vhsNew.getName();
        Integer totalNumber = vhsNew.getTotalNumber();
        List<Vhs> vhsList = vhsService.getAllVhs();
        boolean vhsExists = vhsList.stream().anyMatch(item -> item.getName().equalsIgnoreCase(name));
        if(vhsExists){
            logger.error("A VHS with the name " + name + " already exists.");
            throw new RentalException("A VHS with the name " + name + " already exists.");
        }
        if(vhsNew.getTotalNumber() < 1){
            logger.error("The total number has to be bigger than 0");
            throw new InvalidNumberException("The total number has to be bigger than 0");

        }
        Vhs vhs = new Vhs();
        vhs.setName(name);
        vhs.setTotalNumber(totalNumber);
        vhs.setNumberInStock(totalNumber);
        Vhs savedVhs = vhsService.save(vhs);
        return new ResponseEntity<>(savedVhs, HttpStatus.CREATED);
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
