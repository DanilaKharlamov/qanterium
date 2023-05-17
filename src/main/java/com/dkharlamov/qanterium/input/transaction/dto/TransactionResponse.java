package com.dkharlamov.qanterium.input.transaction.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "Transaction", description = "Response transaction with all attributes")
public class TransactionResponse {
    @Schema(description = "Unique identifier",
            minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "1543")
    private Long id;

    @Schema(description = "Name of the transaction",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Example transaction")
    private String name;

    @Schema(description = "Description of the transaction",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Description example")
    private String description;

    @Schema(description = "Description of the transaction",
            minLength = 10, maxLength = 10,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "2022-04-15")
    private String createdBy;

    @ArraySchema(arraySchema = @Schema(
            description = "Attributes of transaction values", example = "Carl"),
            minItems = 1, maxItems = 200)
    private List<TransactionValueResponse> transactionValueResponse;

    @Schema(description = "Owner member of the transaction",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "Petr")
    private MemberResponse owner;

    @ArraySchema(arraySchema = @Schema(
            description = "Debt members of the transaction", example = "Jon"),
            minItems = 1, maxItems = 200)
    private List<MemberResponse> debts;
}
