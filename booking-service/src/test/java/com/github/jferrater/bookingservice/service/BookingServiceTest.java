package com.github.jferrater.bookingservice.service;

import com.github.jferrater.bookingservice.client.PaymentServiceClient;
import com.github.jferrater.bookingservice.dto.Transaction;
import com.github.jferrater.bookingservice.dto.TransactionRequest;
import com.github.jferrater.bookingservice.dto.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(BookingService.class)
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private PaymentServiceClient paymentServiceClient;
    @Test
    void shouldReturnTwoRejectedTransactions() {
        TransactionRequest transactionRequest1 = new TransactionRequest(
                "jolly.jae@gmail.com", "TR001", 500.0, "Jolly", "Jae");
        TransactionRequest transactionRequest2 = new TransactionRequest(
                "wella.sky@gmail.com", "TR002", 600.0, "Wella", "Sky");
        TransactionRequest transactionRequest3 = new TransactionRequest(
                "hinata.shoyo@gmail.com", "TR003", 1000.0, "Hinata", "Shoyo");

        TransactionResponse mockResponse1 = new TransactionResponse(
                "TR001", "jolly.jae@gmail.com", "Jolly", "Jae", "APPROVED");
        TransactionResponse mockResponse2 = new TransactionResponse(
                "TR002", "wella.sky@gmail.com", "Wella", "Sky", "REJECTED");
        TransactionResponse mockResponse3 = new TransactionResponse(
                "TR003", "hinata.shoyo@gmail.com", "Hinata", "Shoyo", "REJECTED");
        when(paymentServiceClient.authorizeTransaction(transactionRequest1)).thenReturn(Mono.just(mockResponse1));
        when(paymentServiceClient.authorizeTransaction(transactionRequest2)).thenReturn(Mono.just(mockResponse2));
        when(paymentServiceClient.authorizeTransaction(transactionRequest3)).thenReturn(Mono.just(mockResponse3));


        Flux<String> transactionStream = Flux.just(
                "Jolly,Jae,jolly.jae@gmail.com,500,TR001",
                "Wella,Sky,wella.sky@gmail.com,600,TR002",
                "Hinata,Shoyo,hinata.shoyo@gmail.com,1000,TR003"
                );

        StepVerifier.create(bookingService.getRejectedTransactions(transactionStream))
                .assertNext(r -> {
                    List<Transaction> rejectedTransactions = r.getRejectedTransactions();
                    assertEquals(2, rejectedTransactions.size());
                    Transaction transaction1 = rejectedTransactions.get(0);
                    Transaction transaction2 = rejectedTransactions.get(1);
                    assertEquals("wella.sky@gmail.com", transaction1.getEmail());
                    assertEquals("hinata.shoyo@gmail.com", transaction2.getEmail());
                })
                .verifyComplete();

    }

    @Test
    void shouldReturnEmptyRejectedTransaction() {
        TransactionRequest transactionRequest1 = new TransactionRequest(
                "jolly.jae@gmail.com", "TR001", 500.0, "Jolly", "Jae");
        TransactionRequest transactionRequest2 = new TransactionRequest(
                "wella.sky@gmail.com", "TR002", 600.0, "Wella", "Sky");
        TransactionRequest transactionRequest3 = new TransactionRequest(
                "hinata.shoyo@gmail.com", "TR003", 1000.0, "Hinata", "Shoyo");

        TransactionResponse mockResponse1 = new TransactionResponse(
                "TR001", "jolly.jae@gmail.com", "Jolly", "Jae", "APPROVED");
        TransactionResponse mockResponse2 = new TransactionResponse(
                "TR002", "wella.sky@gmail.com", "Wella", "Sky", "APPROVED");
        TransactionResponse mockResponse3 = new TransactionResponse(
                "TR003", "hinata.shoyo@gmail.com", "Hinata", "Shoyo", "APPROVED");
        when(paymentServiceClient.authorizeTransaction(transactionRequest1)).thenReturn(Mono.just(mockResponse1));
        when(paymentServiceClient.authorizeTransaction(transactionRequest2)).thenReturn(Mono.just(mockResponse2));
        when(paymentServiceClient.authorizeTransaction(transactionRequest3)).thenReturn(Mono.just(mockResponse3));


        Flux<String> transactionStream = Flux.just(
                "Jolly,Jae,jolly.jae@gmail.com,500,TR001",
                "Wella,Sky,wella.sky@gmail.com,600,TR002",
                "Hinata,Shoyo,hinata.shoyo@gmail.com,1000,TR003"
        );

        StepVerifier.create(bookingService.getRejectedTransactions(transactionStream))
                .assertNext(r -> {
                    List<Transaction> rejectedTransactions = r.getRejectedTransactions();
                    assertEquals(0, rejectedTransactions.size());
                })
                .verifyComplete();

    }

}