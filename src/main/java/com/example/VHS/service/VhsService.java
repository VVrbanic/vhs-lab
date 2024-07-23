package com.example.VHS.service;

import com.example.VHS.entity.Vhs;
import com.example.VHS.exception.IdNotValidException;
import com.example.VHS.repository.VhsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VhsService {
    private final VhsRepository vhsRepository;

    public VhsService(VhsRepository vhsRepository) {
        this.vhsRepository = vhsRepository;
    }

    public Vhs getVhsById(Integer id) {
        return vhsRepository.findById(id).orElseThrow(() -> new IdNotValidException("VHS with id: " + id + " not found"));
    }

    public List<Vhs> getAllVhs(){
        return vhsRepository.findAll();
    }
    public Vhs save(Vhs vhs) {
        return vhsRepository.save(vhs);
    }

}
