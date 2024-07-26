package com.example.VHS.service;

import com.example.VHS.entity.Vhs;
import com.example.VHS.repository.VhsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VhsServiceTest {

    @Mock
    private VhsRepository vhsRepository;

    private VhsService underTest;

    @BeforeEach
    void setUp(){

        underTest = new VhsService(vhsRepository);
    }

    @Test
    void canGetAllVhs() {
        //when
        underTest.getAllVhs();
        //then
        verify(vhsRepository).findAll();
    }

    @Disabled
    @Test
    void canSaveVhs() {
    }
}