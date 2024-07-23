package com.example.VHS.service;

import com.example.VHS.entity.Price;
import com.example.VHS.exception.RentalException;
import com.example.VHS.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PriceService {

    public static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price save(Price price, LocalDateTime time){
        Optional<Price> optionalActivePrice = getActivePrice();
        if(optionalActivePrice.isEmpty()){
            logger.warn("There is no active price!");
            throw new RentalException("There is no active price, the new price has been added and set to the active price");
        }else{
            Price activePrice = optionalActivePrice.get();
            activePrice.setActive(Boolean.FALSE);
            activePrice.setDate_until(time);
            logger.info("The price has been changed:" + activePrice.getPrice() + "to:" + price.getPrice());
        }
        return priceRepository.save(price);
    }
    public List<Price> getAllPrices() {

        return priceRepository.findAll();
    }

    public Optional<Price> getActivePrice(){
        List<Price> priceList = priceRepository.findAll();
        return priceList.stream().filter(Price::getActive).findFirst();
    }
}
