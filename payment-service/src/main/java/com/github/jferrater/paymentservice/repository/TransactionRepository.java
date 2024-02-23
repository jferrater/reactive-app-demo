package com.github.jferrater.paymentservice.repository;

import com.github.jferrater.paymentservice.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
}
