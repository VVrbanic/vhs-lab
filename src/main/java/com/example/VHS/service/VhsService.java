package com.example.VHS.service;

import com.example.VHS.entity.Vhs;
import com.example.VHS.repository.VhsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VhsService {

    @Autowired
    private VhsRepository vhsRepository;

    public Vhs getVhsById(Integer id) {
        return vhsRepository.findById(id).orElseThrow(() -> new RuntimeException("VHS not found"));
    }

    public List<Vhs> getAllVhs(){
        return vhsRepository.findAll();
    }
    public Vhs save(Vhs vhs) {
        return vhsRepository.save(vhs);
    }

}
