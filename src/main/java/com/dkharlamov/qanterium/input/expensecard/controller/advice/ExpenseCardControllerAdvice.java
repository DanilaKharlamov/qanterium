package com.dkharlamov.qanterium.input.expensecard.controller.advice;

import com.dkharlamov.qanterium.common.util.ErrorUtil;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.expensecard.exception.ExpenseCardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExpenseCardControllerAdvice {
    @Value("${ERRORS_MESSAGES_EXPENSE_CARD:Error when working with the spending card}")
    private String expenseCardErrorMessage;

    @ExceptionHandler(ExpenseCardException.class)
    public ResponseEntity<ErrorDto> handleMemberException(ExpenseCardException exception) {
        log.info("Exception during work with expense card api! {}", exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ErrorUtil.buildError(
                expenseCardErrorMessage,
                exception.getLocalizedMessage(),
                "expense-card-error"
        ));
    }
}
