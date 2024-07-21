package com.example.VHS.service;

import com.example.VHS.entity.Price;
import com.example.VHS.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }
}
