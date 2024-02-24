package com.github.jferrater.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectedTransactionResponse {

    @JsonProperty("Rejected Transactions")
    private List<Transaction> rejectedTransactions;
}
