package com.example.VHS.service;

import com.example.VHS.controller.RentalController;
import com.example.VHS.entity.Price;
import com.example.VHS.entity.Rental;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import com.example.VHS.repository.PriceRepository;
import com.example.VHS.repository.RentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private VhsService vhsService;

    @Autowired
    private PriceService priceService;

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public Rental rentRental(Rental rental) {

        rental.setRentedDate(LocalDateTime.now());
        rental.setDueDate(LocalDateTime.now().plusDays(7));
        rental.setReturnDate(null);
        rental.setDue(null);
        rental.setUnpaidDue(null);

        // Save rental to the database
        Rental rentRental = rentalRepository.save(rental);
        decreaseVHSStock(rental.getVhs());

        return rentRental;
    }

    public Rental returnRental(Rental rental) {
        if(rental.getReturnDate().isAfter(rental.getDueDate())){
            Integer noDaysLate = (int) java.time.temporal.ChronoUnit.DAYS.between(rental.getDueDate(), rental.getReturnDate());
            List<Price> priceList = priceService.getAllPrices();
            Optional<Price> activePrice = priceList.stream()
                    .filter(price -> Boolean.TRUE.equals(price.getActive()))
                    .findFirst();
            //Optional error
            Float fee = activePrice.get().getPrice();
            rental.setDue(fee*noDaysLate);
            rental.setUnpaidDue(fee*noDaysLate);
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
