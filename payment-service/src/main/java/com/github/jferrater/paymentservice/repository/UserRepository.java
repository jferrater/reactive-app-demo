package com.github.jferrater.paymentservice.repository;

import com.github.jferrater.paymentservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, UUID> {

    public Mono<UserEntity> findByEmail(String email);
}
