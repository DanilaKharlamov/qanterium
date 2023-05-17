package com.dkharlamov.qanterium.input.transaction.controller.advice;

import com.dkharlamov.qanterium.common.util.ErrorUtil;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.transaction.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class TransactionControllerAdvice {
    @Value("${ERRORS_MESSAGES_TRANSACTION:Error when working with transactions}")
    private String transactionErrorMessage;

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorDto> handleMemberException(TransactionException exception) {
        log.info("Exception during work with transactions api! {}", exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ErrorUtil.buildError(
                transactionErrorMessage,
                exception.getLocalizedMessage(),
                "transaction-error"
        ));
    }
}
