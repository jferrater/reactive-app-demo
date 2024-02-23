package com.github.jferrater.paymentservice.service;

import com.github.jferrater.paymentservice.dto.TransactionRequest;
import com.github.jferrater.paymentservice.dto.User;
import com.github.jferrater.paymentservice.entity.UserEntity;
import com.github.jferrater.paymentservice.repository.UserRepository;
import com.github.jferrater.paymentservice.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> updateUserBalance(UserEntity user) {
        return this.userRepository.save(user)
                .map(UserMapper::toDto);
    }

    public Mono<UserEntity> updateBalance(TransactionRequest transactionRequest) {
        return this.userRepository.findByEmail(transactionRequest.getEmail())
                .filter(user -> user.getBalance() > transactionRequest.getAmount())
                .map(user -> new UserEntity(user.getId(), user.getEmail(), user.getBalance() - transactionRequest.getAmount()))
                .flatMap(this.userRepository::save);
    }

    public Flux<User> getUsers() {
        return this.userRepository.findAll()
                .map(UserMapper::toDto);
    }
}