package com.dkharlamov.qanterium.common.util;

import com.dkharlamov.qanterium.common.dto.ErrorDto;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ErrorUtil {

    private ErrorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static ErrorDto buildError(
            @NonNull String title,
            String detailMessage,
            String... detailCodes
    ) {
        return ErrorDto.builder()
                .code(constructErrorCode(detailCodes))
                .title(title)
                .detail(StringUtils.isNotBlank(detailMessage) ? detailMessage : COMMON_ERROR_MESSAGE)
                .build();
    }

    public static String constructErrorCode(String... detailCodes) {
        StringJoiner joiner = new StringJoiner(".");

        String details = Stream.of(detailCodes)
                .filter(Objects::nonNull)
                .map(detailCode -> detailCode.replace(" ", "").toLowerCase())
                .collect(Collectors.joining("."));
        if (Strings.isNotBlank(details)) {
            joiner.add(details);
        }
        return joiner.toString();
    }

    public static final String COMMON_ERROR_CODE = "common-error";
    public static final String COMMON_ERROR_TITLE = "Common error";
    public static final String COMMON_ERROR_MESSAGE = "Common request error";

    public static final String SYNTAX_SCHEMA_ERROR_CODE = "syntax-schema-error";
    public static final String SYNTAX_SCHEMA_ERROR_TITLE = "Syntax schema error";

    public static final String SERVICE_UNAVAILABLE_ERROR_CODE = "service-unavailable-error";
    public static final String SERVICE_UNAVAILABLE_ERROR_TITLE = "Service unavailable";
    public static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE = "The service is not available at this time. Please try again later";

}
