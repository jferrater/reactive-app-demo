package com.github.jferrater.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {

    @JsonProperty("Email")
    private String email;
    @JsonProperty("Transaction Number")
    private String transactionNumber;
    @JsonProperty("Amount")
    private Double amount;
    @JsonProperty("First Name")
    private String firstName;
    @JsonProperty("Last Name")
    private String lastName;
}
