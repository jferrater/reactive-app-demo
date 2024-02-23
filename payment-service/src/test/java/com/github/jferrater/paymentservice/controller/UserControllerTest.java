package com.github.jferrater.paymentservice.controller;

import com.github.jferrater.paymentservice.dto.User;
import com.github.jferrater.paymentservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
@Import(UserService.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void testGetUsers() {
        User user1 = new User(UUID.randomUUID().toString(), "jolly.jae@gmail.com", "Jolly", "Jae", 45.0);
        User user2 = new User(UUID.randomUUID().toString(), "keerthi.pass@gmail.com", "Keerthi", "Pass", 45.0);
        when(userService.getUsers()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(2);

    }
}