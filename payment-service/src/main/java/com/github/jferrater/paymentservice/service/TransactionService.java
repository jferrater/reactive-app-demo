package com.github.jferrater.paymentservice.service;

import com.github.jferrater.paymentservice.dto.TransactionRequest;
import com.github.jferrater.paymentservice.dto.TransactionResponse;
import com.github.jferrater.paymentservice.dto.TransactionStatus;
import com.github.jferrater.paymentservice.entity.TransactionEntity;
import com.github.jferrater.paymentservice.repository.TransactionRepository;
import com.github.jferrater.paymentservice.util.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public Mono<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        return this.userService.updateBalance(transactionRequest)
                .map(user -> new TransactionEntity(
                        transactionRequest.getRequestId(),
                        user.getEmail(),
                        transactionRequest.getAmount(),
                        TransactionStatus.APPROVED.toString())
                )
                .flatMap(this.transactionRepository::save)
                .map(TransactionMapper::toTransactionResponse)
                .defaultIfEmpty(
                        new TransactionResponse(
                                transactionRequest.getRequestId(),
                                transactionRequest.getEmail(),
                                TransactionStatus.REJECTED.toString()
                        )
                );
    }

}
