package com.example.VHS.service;

import com.example.VHS.entity.User;
import com.example.VHS.exception.RentalException;
import com.example.VHS.exception.ResourceNotFoundException;
import com.example.VHS.exception.UserDeletionException;
import com.example.VHS.repository.RentalRepository;
import com.example.VHS.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public UserService(UserRepository userRepository, RentalRepository rentalRepository){
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void deleteUserById(Integer id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with this is not found:" + id);
        }
        if(rentalRepository.existsByUserId(id)){
            throw new UserDeletionException("User cannot be deleted because there are rentals associated with him");
        }
        userRepository.deleteById(id);
    }
}
