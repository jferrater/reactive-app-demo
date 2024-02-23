package com.github.jferrater.paymentservice.service;

import com.github.jferrater.paymentservice.dto.TransactionRequest;
import com.github.jferrater.paymentservice.dto.TransactionStatus;
import com.github.jferrater.paymentservice.entity.TransactionEntity;
import com.github.jferrater.paymentservice.entity.UserEntity;
import com.github.jferrater.paymentservice.repository.TransactionRepository;
import com.github.jferrater.paymentservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(TransactionService.class)
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    void testApprovedTransactions() {
        String email = "jolly.jae@gmail.com";
        UUID id = UUID.randomUUID();
        UserEntity user = new UserEntity(id, email, 1000.0);
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        UserEntity after = new UserEntity(id, email, 500.0);
        when(userRepository.save(after)).thenReturn(Mono.just(after));


        TransactionEntity transactionEntity = new TransactionEntity("TR001", email, 500.0, TransactionStatus.APPROVED.toString());
        when(transactionRepository.save(transactionEntity)).thenReturn(Mono.just(transactionEntity));

        TransactionRequest transactionRequest = new TransactionRequest(email, "TR001", 500.0);

        when(userService.updateBalance(transactionRequest)).thenReturn(Mono.just(after));

        StepVerifier.create(transactionService.createTransaction(transactionRequest))
                .assertNext(transactionResponse -> {
                    assertEquals(TransactionStatus.APPROVED.toString(), transactionResponse.getStatus());
                }).verifyComplete();
    }

    @Test
    void testRejectedTransactions() {
        String email = "jolly.jae@gmail.com";
        UUID id = UUID.randomUUID();
        UserEntity user = new UserEntity(id, email, 300.0); // not enough balance to process the transaction request
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        TransactionEntity transactionEntity = new TransactionEntity("TR001", email, 500.0, TransactionStatus.REJECTED.toString());
        when(transactionRepository.save(transactionEntity)).thenReturn(Mono.just(transactionEntity));

        TransactionRequest transactionRequest = new TransactionRequest(email, "TR001", 500.0);

        when(userService.updateBalance(transactionRequest)).thenReturn(Mono.empty());

        StepVerifier.create(transactionService.createTransaction(transactionRequest))
                .assertNext(transactionResponse -> {
                    assertEquals(TransactionStatus.REJECTED.toString(), transactionResponse.getStatus());
                }).verifyComplete();
    }
}