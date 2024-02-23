package com.github.jferrater.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RejectedTransactionResponse {

    @JsonProperty("Rejected Transactions")
    private List<Transaction> rejectedTransactions;
}
