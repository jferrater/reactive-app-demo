package com.github.jferrater.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jferrater.bookingservice.dto.RejectedTransactionResponse;
import com.github.jferrater.bookingservice.dto.Transaction;
import com.github.jferrater.bookingservice.dto.TransactionResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingControllerTest {

    static int MOCK_SERVER_PORT;

    static {
        try (var serverSocket = new ServerSocket(0)) {
            MOCK_SERVER_PORT = serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static MockWebServer mockPaymentServiceBackend;
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void paymentServiceProperties(DynamicPropertyRegistry registry) {
        registry.add("payment-service.base-url", () -> "http://localhost:" + MOCK_SERVER_PORT);
    }


    @BeforeEach
    void setup() throws Exception {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
        mockPaymentServiceBackend = new MockWebServer();
        mockPaymentServiceBackend.start(MOCK_SERVER_PORT);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockPaymentServiceBackend.shutdown();
    }


    @Test
    void shouldReturnOneRejectedBooking() throws IOException {
        String email = "jolly.jae@gmail.com";
        String transactionId = "TR001";
        String firstName = "Jolly";
        String lastName = "Jae";
        String status = "REJECTED";
        TransactionResponse mockResponse = new TransactionResponse(transactionId, email, firstName, lastName, status);
        mockPaymentServiceBackend.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(mockResponse))
                .addHeader("Content-type", "application/json")
        );

        Flux<String> bodyflux = Flux.just(
                "Jolly,Jae,jolly.jae@gmail.com,500,TR001",
                "Wella,Sky,wella.sky@gmail.com,600,TR002",
                "Hinata,Shoyo,hinata.shoyo@gmail.com,1000,TR003"
        );

        webTestClient.post()
                .uri("/rejected_transactions")
                .accept(MediaType.APPLICATION_JSON)
                .body(bodyflux, String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RejectedTransactionResponse.class)
                .consumeWith(response -> {
                    List<Transaction> rejectedTransactions = response.getResponseBody().getRejectedTransactions();
                    assertEquals(1, rejectedTransactions.size());
                    Transaction transaction = rejectedTransactions.get(0);
                    assertEquals(email, transaction.getEmail());
                    assertEquals(firstName, transaction.getFirstName());
                    assertEquals(lastName, transaction.getLastName());
                });;

    }

    @Test
    void shouldReturnEmptyRejectedBookings() throws IOException {
        TransactionResponse mockResponse1 = new TransactionResponse("TR001", "jolly.jae@gmail.com", "Jolly", "Jae", "APPROVED");
        TransactionResponse mockResponse2 = new TransactionResponse("TR002", "wella.sky@gmail.com", "Wella", "Sky", "APPROVED");
        TransactionResponse mockResponse3 = new TransactionResponse("TR003", "hinata.shoyo@gmail.com", "Hinata", "Shoyo", "APPROVED");
        mockPaymentServiceBackend.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(mockResponse1))
                .addHeader("Content-type", "application/json")
        );
        mockPaymentServiceBackend.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(mockResponse2))
                .addHeader("Content-type", "application/json")
        );
        mockPaymentServiceBackend.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(mockResponse3))
                .addHeader("Content-type", "application/json")
        );


        Flux<String> bodyflux = Flux.just(
                "Jolly,Jae,jolly.jae@gmail.com,500,TR001",
                "Wella,Sky,wella.sky@gmail.com,600,TR002",
                "Hinata,Shoyo,hinata.shoyo@gmail.com,1000,TR003"
        );

        webTestClient.post()
                .uri("/rejected_transactions")
                .accept(MediaType.APPLICATION_JSON)
                .body(bodyflux, String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RejectedTransactionResponse.class)
                .consumeWith(response -> {
                    List<Transaction> rejectedTransactions = response.getResponseBody().getRejectedTransactions();
                    assertEquals(0, rejectedTransactions.size());
                });;

    }
}