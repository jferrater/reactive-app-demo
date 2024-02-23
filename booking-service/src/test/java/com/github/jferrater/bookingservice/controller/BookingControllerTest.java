package com.github.jferrater.bookingservice.controller;

import com.github.jferrater.bookingservice.dto.RejectedTransactionResponse;
import com.github.jferrater.bookingservice.dto.Transaction;
import com.github.jferrater.bookingservice.service.BookingService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebFluxTest(BookingController.class)
@Import(BookingService.class)
class BookingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookingService bookingService;

    @Disabled // TODO: Fix this test, mockito is not happy with flux as parameter in the getRejectedTransactions
    @Test
    void shouldReturnOneRejectedBooking() {

        RejectedTransactionResponse rejectedTransactionResponse = new RejectedTransactionResponse(
                List.of(new Transaction("Jolly", "Jae", "jolly.jae@gmail.com", "TR001"))
        );

        Flux<String> fluxStrings = Flux.just(
                "Jolly,Jae,jolly.jae@gmail.com,500,TR001",
                "Wella,Sky,wella.sky@gmail.com,600,TR002",
                "Hinata,Shoyo,hinata.shoyo@gmail.com,1000,TR003"
        );
        when(bookingService.getRejectedTransactions(fluxStrings)).thenReturn(Mono.just(rejectedTransactionResponse));


        webTestClient.post()
                .uri("/rejected_transactions")
                .contentType(MediaType.TEXT_PLAIN)
                .body(fluxStrings, String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RejectedTransactionResponse.class)
                .consumeWith(response -> {
                    List<Transaction> rejectedTransactions = response.getResponseBody().getRejectedTransactions();
                    assertEquals(1, rejectedTransactions.size());
                });;

    }
}