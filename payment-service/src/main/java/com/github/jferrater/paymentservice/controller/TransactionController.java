package com.github.jferrater.paymentservice.controller;

import com.github.jferrater.paymentservice.dto.TransactionRequest;
import com.github.jferrater.paymentservice.dto.TransactionResponse;
import com.github.jferrater.paymentservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authorize_transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponse> createTransaction(@RequestBody Mono<TransactionRequest> transactionRequestMono) {
        return transactionRequestMono.flatMap(this.transactionService::createTransaction);
    }
}
