package com.example.VHS.repository;

import com.example.VHS.entity.Rental;
import com.example.VHS.entity.User;
import com.example.VHS.entity.Vhs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RentalRepositoryTest {

    @Autowired
    private RentalRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VhsRepository vhsRepository;

    @Test
    void itShouldCheckIfRentalExistsByUserId() {
        // given
        int userId = 1;
        Rental rental = new Rental();

        User user = new User();
        user.setName("Jane Doe");
        user.setUserName("janedoe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        Vhs vhs = new Vhs();
        vhs.setName("New movie");
        vhs.setTotalNumber(3);
        vhs.setNumberInStock(3);
        vhsRepository.save(vhs);

        rental.setRentedDate(LocalDateTime.now());
        rental.setDueDate(LocalDateTime.now().plusDays(7));
        rental.setUser(user);
        rental.setVhs(vhs);
        underTest.save(rental);

        // when
        boolean exists = underTest.existsByUserId(user.getId());

        // then
        assertThat(exists).isTrue();
    }
}
