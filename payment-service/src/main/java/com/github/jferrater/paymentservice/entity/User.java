package com.github.jferrater.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private UUID id;
    private String email;
    private Double balance;
}
