package com.github.jferrater.bookingservice.service;

import com.github.jferrater.bookingservice.client.PaymentServiceClient;
import com.github.jferrater.bookingservice.dto.RejectedTransactionResponse;
import com.github.jferrater.bookingservice.dto.Transaction;
import com.github.jferrater.bookingservice.dto.TransactionRequest;
import com.github.jferrater.bookingservice.dto.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@AllArgsConstructor
@Service
public class BookingService {

    private final PaymentServiceClient paymentServiceClient;
    public Mono<RejectedTransactionResponse> getRejectedTransactions(Flux<String> transactions) {
        return transactions.map(this::toTransactionRequest)
                .log()
                .flatMap(paymentServiceClient::authorizeTransaction)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .filter(transactionResponse -> "REJECTED".equals(transactionResponse.getStatus()))
                .map(this::toTransactionDto)
                .collectList()
                .map(RejectedTransactionResponse::new);

    }

    private TransactionRequest toTransactionRequest(String transaction) {
        String[] split = transaction.split(",", 5);
        String firstName = split[0];
        String lastName = split[1];
        String email = split[2];
        Double amount = Double.valueOf(split[3]);
        String transactionId = split[4];
        return new TransactionRequest(email, transactionId, amount, firstName, lastName);
    }

    private Transaction toTransactionDto(TransactionResponse transactionResponse) {
        return new Transaction(
                transactionResponse.getFirstName(),
                transactionResponse.getLastName(),
                transactionResponse.getEmail(),
                transactionResponse.getTransactionNumber()
        );
    }
}
