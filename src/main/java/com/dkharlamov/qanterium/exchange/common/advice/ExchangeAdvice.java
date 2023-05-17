package com.dkharlamov.qanterium.exchange.common.advice;

import com.dkharlamov.qanterium.common.util.ErrorUtil;
import com.dkharlamov.qanterium.exchange.common.exception.ExchangeException;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExchangeAdvice {
    @Value("${ERRORS_MESSAGES_EXCHANGE:Error when working with the service of price calculation in currencies}")
    private String exchangeErrorMessage;

    @ExceptionHandler(ExchangeException.class)
    public ResponseEntity<ErrorDto> handleMemberException(ExchangeException exception) {
        log.info("Exception during work with exchange rate calculator! {}", exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ErrorUtil.buildError(
                exchangeErrorMessage,
                exception.getLocalizedMessage(),
                "exchange-error"
        ));
    }
}
