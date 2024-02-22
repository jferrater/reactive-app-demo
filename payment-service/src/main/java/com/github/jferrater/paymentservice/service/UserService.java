package com.github.jferrater.paymentservice.service;

import com.github.jferrater.paymentservice.entity.User;
import com.github.jferrater.paymentservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
