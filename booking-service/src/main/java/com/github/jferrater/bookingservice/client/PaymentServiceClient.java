package com.github.jferrater.bookingservice.client;


import com.github.jferrater.bookingservice.dto.TransactionRequest;
import com.github.jferrater.bookingservice.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentServiceClient {

    private final WebClient webClient;

    public PaymentServiceClient(@Value("${payment-service.base-url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponse> authorizeTransaction(TransactionRequest transactionRequest) {
        return this.webClient
                .post()
                .uri("authorize_transaction")
                .bodyValue(transactionRequest)
                .retrieve()
                .bodyToMono(TransactionResponse.class);
    }
}
