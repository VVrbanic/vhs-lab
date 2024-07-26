package com.example.VHS.service;

import com.example.VHS.entity.Price;
import com.example.VHS.entity.User;
import com.example.VHS.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.oauth2.login.OAuth2LoginSecurityMarker;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    private PriceService underTest;

    @BeforeEach
    void setUp(){

        underTest = new PriceService(priceRepository);
    }

    //public Price save(Price price, LocalDateTime time)
    @Test
    void CanSavePriceWhenNoActive() {
        //given
        Float priceEur = 1.0f;
        Price price = new Price();
        price.setPrice(priceEur);
        price.setActive(Boolean.TRUE);

        //when
        Price createPrice = underTest.save(price, LocalDateTime.now());

        //then
        ArgumentCaptor<Price> priceArgumentCaptor = ArgumentCaptor.forClass(Price.class);
        verify(priceRepository).save(priceArgumentCaptor.capture());
        Price capturedPrice = priceArgumentCaptor.getValue();
        assertThat(capturedPrice).isEqualTo(price);
    }



    @Disabled
    @Test
    void canSavePriceWhenActive() {
    }

    //public List<Price> getAllPrices()
    @Test
    void getAllPrices() {
        //when
        underTest.getAllPrices();
        //then
        verify(priceRepository).findAll();
    }

    //public Optional<Price> getActivePrice()

    @Test
    void DoesGetActivePriceWhenActivePriceExists() {
        Float priceEurOne = 1.0f;
        Float priceEurTwo = 1.2f;

        Price priceOne = new Price();
        priceOne.setPrice(priceEurOne);
        priceOne.setDateFrom(LocalDateTime.now());
        priceOne.setActive(Boolean.FALSE);

        Price priceTwo = new Price();
        priceOne.setPrice(priceEurTwo);
        priceOne.setDateFrom(LocalDateTime.now());
        priceTwo.setActive(Boolean.TRUE);

        when(priceRepository.findAll()).thenReturn(Arrays.asList(priceOne, priceTwo));

        Optional<Price> result = underTest.getActivePrice();

        assertEquals(Optional.of(priceTwo), result, "The active price should be returned");
    }

    @Test
    void DoesNotGetActivePriceWhenNoActivePriceExists() {
        Float priceEurOne = 1.0f;
        Float priceEurTwo = 1.2f;

        Price priceOne = new Price();
        priceOne.setPrice(priceEurOne);
        priceOne.setDateFrom(LocalDateTime.now());
        priceOne.setActive(Boolean.FALSE);

        Price priceTwo = new Price();
        priceOne.setPrice(priceEurTwo);
        priceOne.setDateFrom(LocalDateTime.now());
        priceTwo.setActive(Boolean.FALSE);

        when(priceRepository.findAll()).thenReturn(Arrays.asList(priceOne, priceTwo));

        Optional<Price> result = underTest.getActivePrice();

        assertEquals(Optional.empty(), result, "The result should be empty when no active price exists");
    }

    @Test
    void DoesNotGetActivePriceWhenPriceListIsEmpty() {
        when(priceRepository.findAll()).thenReturn(Arrays.asList());

        Optional<Price> result = underTest.getActivePrice();

        assertEquals(Optional.empty(), result, "The result should be empty when the price list is empty");
    }
}