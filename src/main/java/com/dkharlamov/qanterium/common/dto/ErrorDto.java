package com.dkharlamov.qanterium.common.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Schema(title = "Error", description = "Information about the error that occurred during the request")
public class ErrorDto {
    @Schema(description = "Brief description of the error", requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1, maxLength = 200,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Request format error")
    private String title;

    @Schema(description = "Error description", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Failed to disassemble the body of the request")
    private String detail;

    @Schema(description = "Unique error code. In terms of system, service and code: {error-code}.{error-code}",
            requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 100,
            pattern = SwaggerConstants.ERROR_CODE_PATTERN, example = "common-error")
    private String code;
}
