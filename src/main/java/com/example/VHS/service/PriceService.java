package com.example.VHS.service;

import com.example.VHS.entity.Price;
import com.example.VHS.entity.PriceValidation;
import com.example.VHS.exception.PriceForTomorrowAdded;
import com.example.VHS.exception.RentalException;
import com.example.VHS.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
public class PriceService {

    public static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void activatePricesForNextDay(){
        priceRepository.deactivateAllActivePrices();
        priceRepository.activatePricesForDate();
    }
    public Price save(PriceValidation priceValidation){

        Price price = new Price();
        price.setPrice(priceValidation.getPrice());

        //date
        LocalDate nextDay = LocalDate.now().plusDays(1);
        LocalDateTime startOfNextDay = LocalDateTime.of(nextDay, LocalTime.MIDNIGHT);
        price.setDateFrom(startOfNextDay);

        price.setActive(Boolean.FALSE);
        Optional<Price> optionalActivePrice = getActivePrice();

        //ako ima ili nema aktivne cijene
        if(optionalActivePrice.isEmpty()){
            logger.warn("There is no active price!");
        }else{
            Price activePrice = optionalActivePrice.get();
            if(activePrice.getDateUntil() != null){
                throw new PriceForTomorrowAdded("Price for tomorrow already added, try again tomorrow!");
            }
            else{
                LocalDate currentDay = LocalDate.now();
                LocalDateTime endOfDay = LocalDateTime.of(currentDay, LocalTime.of(23,59, 59));
                activePrice.setDateUntil(endOfDay);
                priceRepository.save(activePrice);
                logger.info("The price has been changed:" + activePrice.getPrice() + "to:" + price.getPrice());
            }
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
