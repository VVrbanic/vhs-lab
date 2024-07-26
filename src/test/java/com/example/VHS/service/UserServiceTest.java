package com.example.VHS.service;

import com.example.VHS.entity.User;
import com.example.VHS.repository.RentalRepository;
import com.example.VHS.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService underTest;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Disabled
    @Test
    void CanCreateUser() {
        // given
        User user = new User();
        user.setName("Jane Doe");
        user.setUserName("janedoe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password123");

        // Mock the behavior of passwordEncoder
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Print debug statement before method call
        System.out.println("PasswordEncoder is: " + passwordEncoder);

        // when
        User createdUser = underTest.create(user);

        // Print debug statement after method call
        System.out.println("User created " + createdUser);

        // then (capture the userRepository.save and compare it to user)
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        // Assert the password was encoded
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);

        // Assert the other fields remain unchanged
        assertThat(capturedUser.getName()).isEqualTo(user.getName());
        assertThat(capturedUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Disabled
    @Test
    void CanDeleteUserByIdIfUserNotFound() {
    }

    @Disabled
    @Test
    void CanDeleteUserByIdIfUserHasRentals() {
    }


    @Test
    void CanDeleteUserById() {
        // Given
        User user = new User();
        user.setName("Jane Doe");
        user.setUserName("janedoe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password123");

        // then
        user = underTest.create(user);
        System.out.println(user);

        // when
        assertThat(userRepository.findById(user.getId()));
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isNotPresent();

    }
}
