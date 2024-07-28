package com.example.VHS.controller;

import com.example.VHS.entity.User;
import com.example.VHS.exception.DuePaidException;
import com.example.VHS.exception.NoDueException;
import com.example.VHS.exception.RentalException;
import com.example.VHS.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> usersList =  userService.getAllUsers();
        if(usersList.isEmpty()){
            logger.error("There is no users in the user table");
            throw new RentalException("No users found!");
        }
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
    public ResponseEntity<User> payDue(@RequestParam Integer id, @RequestParam Float payment){
        User user = userService.getUserById(id);
        Float currentDue = user.getUnpaidDue();
        if(currentDue == 0){
            logger.info("The user has no due");
            throw new NoDueException("The user has no due");
        }else if(currentDue <= payment){
            logger.info("The due is paid, the change is: " + (payment - currentDue));
            return new ResponseEntity<>(user, HttpStatus.OK);

        }else{
            Float newDue = currentDue - payment;
            user.setUnpaidDue(newDue);
            userService.update(user);
            logger.info("A part of the due is paid, the user still owns: " + newDue);
            return new ResponseEntity<>(user, HttpStatus.OK);

        }
    }

    @PostMapping("/create")
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user){
        user.setTotalDue(0f);
        user.setUnpaidDue(0f);
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
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
}
