package com.github.jferrater.paymentservice.util;

import com.github.jferrater.paymentservice.dto.TransactionResponse;
import com.github.jferrater.paymentservice.entity.TransactionEntity;

public class TransactionMapper {

    public static TransactionResponse toTransactionResponse(TransactionEntity transactionEntity) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setEmail(transactionEntity.getEmail());
        transactionResponse.setFirstName(transactionEntity.getFirstname());
        transactionResponse.setLastName(transactionEntity.getLastName());
        transactionResponse.setTransactionNumber(transactionEntity.getId());
        transactionResponse.setStatus(transactionEntity.getStatus());
        return transactionResponse;
    }
}
