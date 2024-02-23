package com.github.jferrater.paymentservice.controller;

import com.github.jferrater.paymentservice.dto.User;
import com.github.jferrater.paymentservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<User> getUsers() {
        return userService.getUsers();
    }
}
