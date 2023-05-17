package com.dkharlamov.qanterium.input.transaction.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@Schema(title = "transaction value", description = "Response transaction value with all attributes")
public class TransactionValueResponse {
    @Schema(description = "Transaction value",
            minimum = SwaggerConstants.MIN_STRING_LENGTH_1_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "2313.32")
    private BigDecimal value;

    @Schema(description = "Currency code",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "USD")
    private String currencyCode;

    @Schema(description = "Currency name",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "US Dollar")
    private String currencyName;

    @Schema(description = "Exchange rate of this transaction value",
            minimum = SwaggerConstants.MIN_STRING_LENGTH_1_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "1")
    private BigDecimal exchangeRate;

    @Schema(description = "Exchange rate of this transaction value",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            example = "true")
    private Boolean isBaseCurrency;
}
