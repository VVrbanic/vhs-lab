package com.example.VHS.service;

import com.example.VHS.controller.RentalController;
import com.example.VHS.entity.Price;
import com.example.VHS.entity.Rental;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.IdNotValidException;
import com.example.VHS.repository.RentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private static final Logger logger = LoggerFactory.getLogger(RentalService.class);

    private final RentalRepository rentalRepository;

    private final VhsService vhsService;

    private final PriceService priceService;

    private final UserService userService;
    @Autowired
    public RentalService(RentalRepository rentalRepository, VhsService vhsService, PriceService priceService, UserService userService){
        this.vhsService = vhsService;
        this.rentalRepository = rentalRepository;
        this.priceService = priceService;
        this.userService = userService;
    }

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new IdNotValidException("Rental with id: " + id + " not found"));
    }
    public Rental rentRental(Rental rental) {
        rental.setRentedDate(LocalDateTime.now());
        rental.setDueDate(LocalDateTime.now().plusDays(7));
        rental.setReturnDate(null);
        // Save rental to the database
        Rental rentRental = rentalRepository.save(rental);
        decreaseVHSStock(rental.getVhs());

        return rentRental;
    }

    public Rental returnRental(Rental rental) {
        if(rental.getReturnDate().isAfter(rental.getDueDate())){
            BigDecimal numDaysLate = new BigDecimal((int) java.time.temporal.ChronoUnit.DAYS.between(rental.getDueDate(), rental.getReturnDate()));
            List<Price> priceList = priceService.getAllPrices();
            Optional<Price> activePrice = priceList.stream()
                    .filter(price -> Boolean.TRUE.equals(price.getActive()))
                    .findFirst();
            //Optional error
            BigDecimal fee = activePrice.get().getPrice();
            User user = rental.getUser();
            userService.save(user, fee.multiply(numDaysLate));
            logger.info(user.getName() + "has a new due of "+ fee.multiply(numDaysLate) + "and now the total unpaid due is " + user.getUnpaidDue());
        }
        Rental returnRental = rentalRepository.save(rental);
        increaseVHSStock(rental.getVhs());

        return returnRental;
    }

    private void decreaseVHSStock(Vhs vhs) {
        if (vhs != null && vhs.getNumberInStock() > 0) {
            // Decrease stock by 1
            vhs.setNumberInStock(vhs.getNumberInStock() - 1);
            // Save updated VHS item
            vhsService.save(vhs);
            logger.info("Number of VHS decreased by 1");

        } else {
            logger.error("Failed to decrease the number of VHS");
            throw new RuntimeException("VHS item not found or out of stock");
        }
    }

    private void increaseVHSStock(Vhs vhs) {
        if (vhs.getNumberInStock() < vhs.getTotalNumber()) {
            // Increase stock by 1
            vhs.setNumberInStock(vhs.getNumberInStock() + 1);
            // Save updated VHS item
            vhsService.save(vhs);
            logger.info("Number of VHS increased by 1");
        } else {
            logger.error("Failed to increase the number of VHS");
            throw new RuntimeException("The total number can't be smaller than the number in the stock");
        }
    }

}
