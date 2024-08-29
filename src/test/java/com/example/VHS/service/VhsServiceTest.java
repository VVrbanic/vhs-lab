package com.example.VHS.service;

import com.example.VHS.entity.Vhs;
import com.example.VHS.repository.VhsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Vhs newVhs = vhsRepository.getReferenceById(createdVhs.getId());
        //Vhs newVhs = vhsRepository.getReferenceById(1);

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
    @Test
    void DoesItGetAllUsers(){
        // given
        Vhs vhs1 = new Vhs();
        vhs1.setName("New movie 1");
        vhs1.setTotalNumber(3);
        vhs1.setNumberInStock(3);
        Vhs createdVhs1 = vhsRepository.save(vhs1);

        Vhs vhs2 = new Vhs();
        vhs2.setName("New movie 2");
        vhs2.setTotalNumber(5);
        vhs2.setNumberInStock(5);
        Vhs createdVhs2 = vhsRepository.save(vhs2);

        // when
        List<Vhs> allVhs = underTest.getAllVhs();

        // then
        assertTrue(allVhs.contains(createdVhs1));
        assertTrue(allVhs.contains(createdVhs2));
        assertEquals(2, allVhs.size());

    }

}
