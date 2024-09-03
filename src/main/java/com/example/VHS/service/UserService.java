package com.example.VHS.service;

import com.example.VHS.controller.RentalController;
import com.example.VHS.entity.User;
import com.example.VHS.entity.UserValidation;
import com.example.VHS.exception.*;
import com.example.VHS.repository.RentalRepository;
import com.example.VHS.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
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
        List<User> usersList= userRepository.findAll();
        if(usersList.isEmpty()){
            logger.error("There is no users in the user table");
            throw new RentalException("No users found!");
        }
        else{
            return usersList;

        }
    }

    public ResponseEntity<User> payDue(Integer id, BigDecimal payment){
        User user = this.getUserById(id);
        BigDecimal currentDue = user.getUnpaidDue();
        if(currentDue.equals(0)){
            logger.info("The user has no due");
            throw new NoDueException("The user has no due");
        }else if(currentDue.compareTo(payment) != 1){
            logger.info("The due is paid, the change is: " + (payment.subtract(currentDue)));
            return new ResponseEntity<>(user, HttpStatus.OK);

        }else{
            BigDecimal newDue = currentDue.subtract(payment);
            user.setUnpaidDue(newDue);
            this.update(user);
            logger.info("A part of the due is paid, the user still owns: " + newDue);
            return new ResponseEntity<>(user, HttpStatus.OK);

        }
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

    public ResponseEntity<User> addUser(UserValidation userValidation) {
        User user = new User();
        user.generateUserFromUserValidation(userValidation);
        user.setTotalDue(BigDecimal.ZERO);
        user.setUnpaidDue(BigDecimal.ZERO);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        checkIfUserExists(user);
        User createdUser = userRepository.save(user);

        if(createdUser.getId() == null){
            throw new IdNotValidException("The user wasn't created try again!");
        }else{
            logger.info("New user created:" + createdUser.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);

        }
    }

    public void checkIfUserExists(User user){
        List<User> userList = this.getAllUsers();
        Predicate<User> emailConflict = userCheck -> user.getEmail().equals(userCheck.getEmail());
        Predicate<User> userNameConflict = userCheck -> user.getUserName().equals(userCheck.getUserName());

        Map<Predicate<User>, String> conditions = Map.of(emailConflict.and(userNameConflict), "A user with this email and user name exist!",
                emailConflict, "A user with this email exists",
                userNameConflict, "A user with this name exists!");

        conditions.entrySet().stream()
                .filter(entry -> userList.stream().anyMatch(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(message -> { throw new UserExistsException(message); });

    }
}
