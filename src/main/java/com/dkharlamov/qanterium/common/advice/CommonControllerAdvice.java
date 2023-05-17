package com.dkharlamov.qanterium.common.advice;

import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.common.util.ErrorUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.StringJoiner;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {
    @Value("${COMMON_ERRORS_MESSAGES_BAD_REQUEST:}")
    private String badRequestMessage;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnexpectedException(Exception e) {
        String detailMessage = !isEmpty(badRequestMessage) ? badRequestMessage : e.getLocalizedMessage();
        String title = ErrorUtil.COMMON_ERROR_TITLE;
        String errorCode = ErrorUtil.COMMON_ERROR_CODE;
        log.error("An unexpected error occurred!", e);

        if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            if (!CollectionUtils.isEmpty(constraintViolations)) {
                var stringJoiner = new StringJoiner("; ");
                constraintViolations.forEach(violation ->
                        stringJoiner.add(StringUtils.joinWith(" ", violation.getInvalidValue(), violation.getMessage()))
                );
                detailMessage = StringUtils.substring(stringJoiner.toString(), 0, 255);
                title = ErrorUtil.SYNTAX_SCHEMA_ERROR_TITLE;
                errorCode = ErrorUtil.SYNTAX_SCHEMA_ERROR_CODE;
            }
        } else if (detailMessage.contains("[503 Service Unavailable]")) {
            title = ErrorUtil.SERVICE_UNAVAILABLE_ERROR_TITLE;
            errorCode = ErrorUtil.SERVICE_UNAVAILABLE_ERROR_CODE;
            detailMessage = ErrorUtil.SERVICE_UNAVAILABLE_ERROR_MESSAGE;
        }
        return ResponseEntity.badRequest().body(ErrorUtil.buildError(
                title,
                detailMessage,
                errorCode
        ));
    }
}
