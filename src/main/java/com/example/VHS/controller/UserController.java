package com.example.VHS.controller;

import com.example.VHS.entity.User;
import com.example.VHS.exception.UsersNotFoundException;
import com.example.VHS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> usersList =  userService.getAllUsers();
        if(usersList.isEmpty()){
            throw new UsersNotFoundException("No users found!");
        }
        return usersList;
    }
}
