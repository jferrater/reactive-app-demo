package com.github.jferrater.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
@Data
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private String id;
    private String email;
    private String firstname;
    private String lastName;
    private Double amount;
    private String status;
}
