package com.github.jferrater.bookingservice.service;

import com.github.jferrater.bookingservice.dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@Import(BookingService.class)
class BookingServiceTest {

    @Test
    void testme() {

        Flux<String> myflux = Flux.just("Jolly,Jae,jolly.jae@gmail.com,340,TR001", "Keerthi,Pass,keerthi.pass@gmail.com,500,TR002", "Joffry,Ferrater,joffry.ferrater@gmail.com,5770,TR003");
        myflux.map(this::toTransactionRequest)
                .flatMap()
                .subscribe();
    }

    private TransactionRequest toTransactionRequest(String transaction) {
        String[] split = transaction.split(",", 5);
        String email = split[2];
        Double amount = Double.valueOf(split[3]);
        String transactionId = split[4];
        System.out.println(email);
        return new TransactionRequest(email, transactionId, amount);
    }
}