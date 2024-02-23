package com.github.jferrater.paymentservice.controller;

import com.github.jferrater.paymentservice.dto.TransactionRequest;
import com.github.jferrater.paymentservice.dto.TransactionResponse;
import com.github.jferrater.paymentservice.dto.TransactionStatus;
import com.github.jferrater.paymentservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebFluxTest(TransactionController.class)
@Import(TransactionService.class)
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testApprovedRequest() {
        String requestId = "TR001";
        String email = "jolly.jae@gmail.com";
        TransactionRequest transactionRequest = new TransactionRequest(email, requestId, 500.0);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setRequestId(requestId);
        transactionResponse.setEmail(email);
        transactionResponse.setStatus(TransactionStatus.APPROVED.toString());
        when(transactionService.createTransaction(transactionRequest)).thenReturn(Mono.just(transactionResponse));

        webTestClient.post()
                .uri("/authorize_transaction")
                .body(Mono.just(transactionRequest), TransactionRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TransactionResponse.class)
                .consumeWith(response -> {
                    assertEquals("APPROVED", response.getResponseBody().getStatus());
                    assertEquals(email, response.getResponseBody().getEmail());
                });;
    }
}