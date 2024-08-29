package com.example.VHS.service;

import static org.mockito.Mockito.*;
import com.example.VHS.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PriceServiceTest {

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceRepository priceRepository;

    @BeforeEach
    void  setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testScheduledTaskExecution() throws InterruptedException {
        priceService.activatePricesForNextDay();

        verify(priceRepository, times(1)).deactivateAllActivePrices();
        verify(priceRepository, times(1)).activatePricesForDate();

    }

}
