package com.github.jferrater.bookingservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jferrater.bookingservice.dto.TransactionRequest;
import com.github.jferrater.bookingservice.dto.TransactionResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(PaymentServiceClient.class)
class PaymentServiceClientTest {

    static MockWebServer mockPaymentServiceBackend;

    private PaymentServiceClient paymentServiceClient;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setup() throws Exception {
        mockPaymentServiceBackend = new MockWebServer();
        mockPaymentServiceBackend.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockPaymentServiceBackend.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockPaymentServiceBackend.getPort());
        paymentServiceClient = new PaymentServiceClient(baseUrl);
    }

    @Test
    void testAuthorizeTransaction() throws Exception {
        String email = "jolly.jae@gmail.com";
        String transactionId = "TR001";
        TransactionRequest request = new TransactionRequest(email, transactionId, 450.0);
        String status = "APPROVED";
        TransactionResponse mockResponse = new TransactionResponse(transactionId, email, status);
        mockPaymentServiceBackend.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(mockResponse))
                .addHeader("Content-type", "application/json")
        );

        Mono<TransactionResponse> transactionResponseMono = paymentServiceClient.authorizeTransaction(request);

        StepVerifier.create(transactionResponseMono)
                .assertNext(transactionResponse -> {
                    assertEquals(status, transactionResponse.getStatus());
                    assertEquals(email, transactionResponse.getEmail());
                    assertEquals(transactionId, transactionResponse.getRequestId());
                })
                .verifyComplete();
    }
}