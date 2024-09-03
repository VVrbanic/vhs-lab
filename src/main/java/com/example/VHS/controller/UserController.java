package com.example.VHS.controller;

import com.example.VHS.entity.User;
import com.example.VHS.entity.UserValidation;
import com.example.VHS.exception.UserExistsException;
import com.example.VHS.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> usersList =  userService.getAllUsers();
        return usersList;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        try{
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (RuntimeException e){
            throw e;
        }
    }
    @PutMapping("/payDue")
    public ResponseEntity<User> payDue(@RequestParam Integer id, @RequestParam BigDecimal payment){
        return userService.payDue(id, payment);
    }

    @PostMapping("/create")
    public ResponseEntity<User> addNewUser(@Valid @RequestBody UserValidation user){
        ResponseEntity<User> response = userService.addUser(user);
        return response;
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
    @ExceptionHandler(UserExistsException.class)
    public Map<String, String> handleConflictException(UserExistsException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("conflict", ex.getMessage());

        return errors;
    }


}
