package com.example.VHS.repositories;

import com.example.VHS.entities.VHS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VHSRepository extends JpaRepository<VHS, Integer> {
    // You can add custom query methods here if needed
}
