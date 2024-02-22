package com.github.jferrater.paymentservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class TransactionEntity {

    @Id
    private String id;
    private String email;
    private Double amount;
    private String status;
}
