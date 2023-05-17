package com.dkharlamov.qanterium.input.expensecard.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(
        title = "Expense Card",
        description = "Request to create or receive a expense card"
)
public class ExpenseCardRequest {
    @Schema(description = "Name of the expense card",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Example card")
    private String name;

    @Schema(description = "Description of the expense card",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Description example")
    private String description;
}
