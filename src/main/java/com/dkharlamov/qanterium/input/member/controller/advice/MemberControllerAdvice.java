package com.dkharlamov.qanterium.input.member.controller.advice;

import com.dkharlamov.qanterium.common.util.ErrorUtil;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.member.exception.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class MemberControllerAdvice {
    @Value("${ERRORS_MESSAGES_MEMBER:Error when working with members of the expense card}")
    private String memberErrorMessage;

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorDto> handleMemberException(MemberException exception) {
        log.info("Exception during work with members api! {}", exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(ErrorUtil.buildError(
                memberErrorMessage,
                exception.getLocalizedMessage(),
                "member-error"
        ));
    }
}
