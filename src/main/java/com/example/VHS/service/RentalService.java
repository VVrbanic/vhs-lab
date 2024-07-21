package com.example.VHS.service;

import com.example.VHS.entities.Rental;
import com.example.VHS.entities.VHS;
import com.example.VHS.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private VHSService vhsService;

    public Rental rentRental(Rental rental) {

        rental.setRentedDate(LocalDateTime.now());
        rental.setDueDate(LocalDateTime.now().plusDays(7));
        rental.setReturnDate(null);

        // Save rental to the database
        Rental rentRental = rentalRepository.save(rental);
        updateVHSStock(rental.getVhs());

        return rentRental;
    }

    private void updateVHSStock(VHS vhs) {
        if (vhs != null && vhs.getNumberInStock() > 0) {
            // Decrease stock by 1
            vhs.setNumberInStock(vhs.getNumberInStock() - 1);
            // Save updated VHS item
            vhsService.save(vhs);
        } else {
            throw new RuntimeException("VHS item not found or out of stock");
        }
    }
}
