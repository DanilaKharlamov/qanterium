package com.dkharlamov.qanterium.input.expensecard.util;

import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardRequest;
import org.apache.commons.lang3.StringUtils;

public final class ExpenseCardValidator {

    private ExpenseCardValidator() {
        throw new AssertionError("Utility class");
    }

    public static void validateExpenseCardRequest(ExpenseCardRequest expenseCardRequest) {
        if (expenseCardRequest == null) {
            throw new IllegalArgumentException("ExpenseCardRequest must not be null");
        }
        if (StringUtils.isEmpty(expenseCardRequest.getName()) && StringUtils.isEmpty(expenseCardRequest.getDescription())) {
            throw new IllegalArgumentException("At least one of the elements of the ExpenseCardRequest request must be non-null");
        }
    }

}
