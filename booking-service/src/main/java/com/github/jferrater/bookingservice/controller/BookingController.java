package com.github.jferrater.bookingservice.controller;

import com.github.jferrater.bookingservice.dto.RejectedTransactionResponse;
import com.github.jferrater.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rejected_transactions")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Mono<RejectedTransactionResponse> createTransaction(@RequestBody Flux<String> transactionFlux) {
        return this.bookingService.getRejectedTransactions(transactionFlux);
    }
}
