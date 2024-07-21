package com.example.VHS.controller;


import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.DuplicateVhsException;
import com.example.VHS.exception.RequestIncorretException;
import com.example.VHS.exception.VhsesNotFoundException;
import com.example.VHS.service.VhsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vhses")
public class VhsController {
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private VhsService vhsService;

    @GetMapping
    public List<Vhs> getAllVhses(){
        List<Vhs> vhsList = vhsService.getAllVhs();
        if(vhsList.isEmpty()){
            throw new VhsesNotFoundException("No VHSes found!");
        }
        return vhsList;
    }

    //add new VHS
    @PostMapping("/create")
    public ResponseEntity<Vhs> createVhs(@RequestParam String name,@RequestParam Integer totalNumber) {
        try {
            if (name.equals("") || totalNumber <= 0) {
                throw new RequestIncorretException("Please check your request!");
            }
            List<Vhs> vhsList = vhsService.getAllVhs();
            boolean vhsExists = vhsList.stream().anyMatch(item -> item.getName().equalsIgnoreCase(name));
            if(vhsExists){
                throw new DuplicateVhsException("A VHS with the name " + name + " already exists.");
            }
            Vhs vhs = new Vhs();
            vhs.setName(name);
            vhs.setTotalNumber(totalNumber);
            vhs.setNumberInStock(totalNumber);


            Vhs savedVhs = vhsService.save(vhs);
            return new ResponseEntity<>(savedVhs, HttpStatus.CREATED);


        } catch (RuntimeException e) {

            logger.error("Error while processing rental request: name={}, totalNumber={}", name, totalNumber, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
