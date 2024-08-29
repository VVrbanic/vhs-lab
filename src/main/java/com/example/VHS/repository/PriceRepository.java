package com.example.VHS.repository;

import com.example.VHS.entity.Price;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
    @Modifying
    @Transactional
    //@Query(value = "UPDATE prices p SET p.active = FALSE WHERE p.active = TRUE AND p.date_until <= DATE_ADD(CURDATE(), INTERVAL 1 DAY)", nativeQuery = true)
    @Query(value = "UPDATE Price p SET p.active = FALSE WHERE p.active = TRUE AND p.dateUntil < CURRENT_DATE")
    void deactivateAllActivePrices();


    @Modifying
    @Transactional
    //@Query(value ="UPDATE prices p SET p.active = TRUE WHERE p.date_from <= DATE_ADD(CURDATE(), INTERVAL 1 DAY) AND (p.date_until IS NULL OR p.date_until >= DATE_ADD(CURDATE(), INTERVAL 1 DAY))", nativeQuery = true)
    @Query("UPDATE Price p SET p.active = TRUE WHERE p.dateFrom <= CURRENT_DATE AND (p.dateUntil IS NULL OR p.dateUntil >= CURRENT_DATE )")
    void activatePricesForDate();
}
