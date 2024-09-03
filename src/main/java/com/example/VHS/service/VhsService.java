package com.example.VHS.service;

import com.example.VHS.entity.Vhs;
import com.example.VHS.entity.VhsValidation;
import com.example.VHS.exception.IdNotValidException;
import com.example.VHS.exception.RentalException;
import com.example.VHS.exception.VhsException;
import com.example.VHS.repository.VhsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


@Service
public class VhsService {
    public final static Logger logger = LoggerFactory.getLogger(VhsService.class);
    private final VhsRepository vhsRepository;

    public VhsService(VhsRepository vhsRepository) {
        this.vhsRepository = vhsRepository;
    }

    public Vhs getVhsById(Integer id) {
        return vhsRepository.findById(id).orElseThrow(() ->
                new IdNotValidException("VHS with id: " + id + " not found"));
    }

    public List<Vhs> getAllVhs(){
        List<Vhs> vhsList = vhsRepository.findAll();
        if(vhsList.isEmpty()){
            throw new RentalException("No VHSes found!");
        }
        return vhsList;
    }
    public Vhs save(Vhs vhs) {
        return vhsRepository.save(vhs);
    }

    public ResponseEntity<Vhs> create(VhsValidation vhsValidation){
        checkIfVhsIsValid(vhsValidation);
        Vhs vhs = new Vhs();
        vhs.setName(vhsValidation.getName());
        vhs.setTotalNumber(vhsValidation.getTotalNumber());
        vhs.setNumberInStock(vhsValidation.getTotalNumber());
        Vhs savedVhs = this.save(vhs);
        return new ResponseEntity<>(savedVhs, HttpStatus.CREATED);
    }

    public void checkIfVhsIsValid(VhsValidation vhsValidation){
        List<Vhs> vhsList = this.getAllVhs();
        Predicate<Vhs> nameConflict = checkName -> checkName.getName().equalsIgnoreCase(vhsValidation.getName());
        Predicate<Vhs> totalNumberConflict = checkNum -> vhsValidation.getTotalNumber() <= 0;

        Map<Predicate<Vhs>, String> conditions = Map.of(nameConflict.and(totalNumberConflict), "A VHS with this name already exists and the total number had to be bigger than 0!",
                nameConflict, "A VHS with this name already exists!!",
                totalNumberConflict, " The total number has to be bigger than 0!!");

        conditions.entrySet().stream()
                .filter(entry -> vhsList.stream().anyMatch(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(message -> {throw new VhsException(message); });
    }

}
