package com.example.VHS.service;

import com.example.VHS.controller.RentalController;
import com.example.VHS.entity.User;
import com.example.VHS.exception.IdNotValidException;
import com.example.VHS.exception.ResourceNotFoundException;
import com.example.VHS.exception.UserDeletionException;
import com.example.VHS.repository.RentalRepository;
import com.example.VHS.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RentalRepository rentalRepository){
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user, BigDecimal fee){
        user.setTotalDue(user.getTotalDue().add(fee));
        user.setUnpaidDue(user.getUnpaidDue().add(fee));
        return userRepository.save(user);
    }
    public User update(User user){
        User userSaved = userRepository.save(user);
        return userSaved;
    }

    public User create(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User userNew = userRepository.save(user);
        return userNew;
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IdNotValidException("User with id: " + id + " not found"));
    }


    @Transactional
    public void deleteUserById(Integer id) {
        if(!userRepository.existsById(id)) {
            logger.error("User with this is not found:" + id);
            throw new ResourceNotFoundException("User with this is not found:" + id);
        }
        if(rentalRepository.existsByUserId(id)){
            logger.error("User cannot be deleted because there are rentals associated with him");
            throw new UserDeletionException("User cannot be deleted because there are rentals associated with him");
        }
        userRepository.deleteById(id);
    }
}
