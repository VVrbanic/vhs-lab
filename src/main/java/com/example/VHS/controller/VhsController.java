package com.example.VHS.controller;

import com.example.VHS.entity.Vhs;
import com.example.VHS.entity.VhsValidation;
import com.example.VHS.exception.RentalException;
import com.example.VHS.exception.UserExistsException;
import com.example.VHS.exception.VhsException;
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
    private static final Logger logger = LoggerFactory.getLogger(VhsController.class);
    private final VhsService vhsService;

    public VhsController(VhsService vhsService) {
        this.vhsService = vhsService;
    }

    @GetMapping
    public List<Vhs> getAllVhses(){
        List<Vhs> vhsList = vhsService.getAllVhs();
        return vhsList;
    }

    //add new VHS
    @PostMapping("/create")
    public ResponseEntity<Vhs> createVhs(@Valid @RequestBody VhsValidation vhsNew) {
        ResponseEntity<Vhs> vhsResponseEntity = vhsService.create(vhsNew);
        return vhsResponseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(VhsException.class)
    public Map<String, String> handleConflictException(VhsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("conflict", ex.getMessage());
        return errors;
    }
}
