package com.dkharlamov.qanterium.input.expensecard.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(
        title = "Expense card",
        description = "Response expense card contains all attribute"
)
public class ExpenseCardResponse {
    @Schema(description = "Unique identifier",
            minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "1543")
    private Long id;

    @Schema(description = "Name of the expense card",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Bar")
    private String name;

    @Schema(description = "Description of the expense card",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Description example")
    private String description;

    @Schema(description = "Date the expense card was created",
            minLength = 10, maxLength = 10,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "2023-01-01")
    private String createdBy;

    @Schema(description = "Date and time the expense card was updated",
            minLength = 20, maxLength = 20,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "2023-05-03T15:30:00")
    private String updatedBy;

    @ArraySchema(arraySchema = @Schema(
            description = "Names or nicknames of the members to be added to the card", nullable = true,
            example = "Carl"),
            maxItems = 200)
    private List<MemberResponse> members;
}
