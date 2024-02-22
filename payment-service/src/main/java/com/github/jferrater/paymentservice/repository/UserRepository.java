package com.github.jferrater.paymentservice.repository;

import com.github.jferrater.paymentservice.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, UUID> {

    public Mono<User> findByEmail(String email);
}
