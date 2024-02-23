package com.github.jferrater.paymentservice.service;

import com.github.jferrater.paymentservice.dto.TransactionRequest;
import com.github.jferrater.paymentservice.entity.UserEntity;
import com.github.jferrater.paymentservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(UserService.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testUserFindByEmail() {
        String email = "jolly.jae@gmail.com";
        String firstName = "Jolly";
        String lastName = "Jae";
        UserEntity user = new UserEntity(UUID.randomUUID(), email, firstName, lastName, 1000.0);
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        StepVerifier.create(userService.findByEmail(email))
                .assertNext( u -> {
                    assertEquals(email, u.getEmail());
                    assertEquals(firstName, u.getFirstName());
                    assertEquals(lastName, u.getLastName());
                    assertEquals(1000.0, u.getBalance());
                })
                .verifyComplete();
    }

    @Test
    void testUpdateBalance() {
        String email = "jolly.jae@gmail.com";
        String firstName = "Jolly";
        String lastName = "Jae";
        UUID id = UUID.randomUUID();
        UserEntity user = new UserEntity(id, email, firstName, lastName, 1000.0);
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        UserEntity after = new UserEntity(id, email, firstName, lastName, 500.0);
        when(userRepository.save(after)).thenReturn(Mono.just(after));

        TransactionRequest transactionRequest = new TransactionRequest(email, "TR001", 500.0, firstName, lastName);
        StepVerifier.create(userService.updateBalance(transactionRequest))
                .assertNext( u -> {
                    assertEquals(email, u.getEmail());
                    assertEquals(firstName, u.getFirstName());
                    assertEquals(lastName, u.getLastName());
                    assertEquals(500.0, u.getBalance());
                })
                .verifyComplete();
    }

    @Test
    void testGetUsers() {
        String firstName = "Jolly";
        String lastName = "Jae";
        UserEntity user1 = new UserEntity(UUID.randomUUID(), "jolly.jae@gmail.com", firstName, lastName, 1000.0);
        UserEntity user2 = new UserEntity(UUID.randomUUID(), "kee.pass@gmail.com", firstName, lastName, 500.0);
        when(userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        StepVerifier.create(userService.getUsers())
                .expectNextCount(2)
                .verifyComplete();

    }

}