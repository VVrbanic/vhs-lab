package com.example.VHS.repository;

import com.example.VHS.entity.Vhs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VhsRepository extends JpaRepository<Vhs, Integer> {
}
