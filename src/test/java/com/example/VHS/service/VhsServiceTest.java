package com.example.VHS.service;

import com.example.VHS.entity.Vhs;
import com.example.VHS.repository.VhsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class VhsServiceTest {

    @Autowired
    private VhsRepository vhsRepository;

    private VhsService underTest;

    @BeforeEach
    void setUp(){
        underTest = new VhsService(vhsRepository);
    }

    @Test
    void DoesItSaveVhs() {
        // given
        Vhs vhs = new Vhs();
        vhs.setName("New movie");
        vhs.setTotalNumber(3);
        vhs.setNumberInStock(3);
        Vhs createdVhs = underTest.save(vhs);

        // when
        Vhs newVhs = vhsRepository.getReferenceById(1);

        // then
        assertEquals(createdVhs, newVhs);
    }

    @Test
    void DoesItGetRightUserById(){
        // given
        Vhs vhs = new Vhs();
        vhs.setName("New movie");
        vhs.setTotalNumber(3);
        vhs.setNumberInStock(3);
        Vhs createdVhs = vhsRepository.save(vhs);

        // when
        Vhs newVhs = underTest.getVhsById(1);

        // then
        assertEquals(createdVhs, newVhs);

    }

}
