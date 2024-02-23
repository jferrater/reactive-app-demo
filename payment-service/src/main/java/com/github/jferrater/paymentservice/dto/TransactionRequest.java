package com.github.jferrater.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {

    @JsonProperty("email")
    private String email;
    @JsonProperty("request-id")
    private String requestId;
    @JsonProperty("amount")
    private Double amount;
}
