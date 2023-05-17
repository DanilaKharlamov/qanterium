package com.dkharlamov.qanterium.input.transaction.util;

import com.dkharlamov.qanterium.input.transaction.dto.TransactionRequest;


public class TransactionValidator {
    private TransactionValidator() {
        throw new AssertionError("Utility class");
    }

    public static void validateTransactionRequest(TransactionRequest request) {
        requireNonNull(request.getName(), "name");
        requireNonNull(request.getExpenseCardId(), "expenseCardId");
        requireNonNull(request.getOwnerId(), "ownerId");
        requireNonNull(request.getCurrency(), "currency");
        requireNonNull(request.getValue(), "value");
    }

    private static void requireNonNull(Object obj, String field) {
        if (obj == null) {
            throw new IllegalArgumentException("TransactionRequest must contain a " + field);
        }
    }
}
