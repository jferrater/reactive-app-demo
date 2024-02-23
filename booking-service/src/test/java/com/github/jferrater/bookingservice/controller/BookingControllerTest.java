package com.github.jferrater.bookingservice.controller;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(BookingController.class)
class BookingControllerTest {

}