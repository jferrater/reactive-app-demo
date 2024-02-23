package com.github.jferrater.bookingservice.service;

import com.github.jferrater.bookingservice.client.PaymentServiceClient;
import com.github.jferrater.bookingservice.dto.Transaction;
import com.github.jferrater.bookingservice.dto.TransactionRequest;
import com.github.jferrater.bookingservice.dto.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
public class BookingService {

    private final PaymentServiceClient paymentServiceClient;
    public Flux<Object> processTransaction(Flux<String> transactions) {

        return transactions.map(this::toTransactionRequest)
                .flatMap(paymentServiceClient::authorizeTransaction)
                .filter(transactionResponse -> "APPROVED".equals(transactionResponse.getStatus())
                .map();

    }

    private TransactionRequest toTransactionRequest(String transaction) {
        String[] split = transaction.split(",", 5);
        String email = split[2];
        Double amount = Double.valueOf(split[3]);
        String transactionId = split[4];
        return new TransactionRequest(email, transactionId, amount);
    }

    private Transaction toTransactionDto(TransactionResponse transactionResponse) {

    }
}
