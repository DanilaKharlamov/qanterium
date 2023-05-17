package com.dkharlamov.qanterium.input.transaction.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(
        title = "Transaction attribute",
        description = "Request for creating or updating the transaction"
)
public class TransactionRequest {

    @Schema(description = "Unique identifier of expense card",
            minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "3213")
    private Long expenseCardId;

    @Schema(description = "Name of the transaction",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Bar")
    private String name;

    @Schema(description = "Description of the transaction",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Description example")
    private String description;

    @Schema(description = "Transaction value",
            minimum = SwaggerConstants.MIN_STRING_LENGTH_1_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "2313.32")
    private Double value;

    @Schema(description = "Transaction base currency",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ALPHANUMERIC_PATTERN, example = "USD")
    private String currency;

    @Schema(description = "Unique identifier of owner",
            minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "1543")
    private Long ownerId;

    @ArraySchema(arraySchema = @Schema(
            description = "Unique identifier of the members", example = "2"),
            minItems = 1, maxItems = 200)
    private List<Long> debtsMemberId = new ArrayList<>();
}
