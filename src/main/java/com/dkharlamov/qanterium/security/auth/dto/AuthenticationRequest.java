package com.dkharlamov.qanterium.security.auth.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Authentication", description = "User authentication request")
public class AuthenticationRequest {
    @Schema(description = "Username/login",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = 25,
            pattern = SwaggerConstants.ALPHANUMERIC_PATTERN, example = "exampleusername")
    private String username;

    @Schema(description = "Password",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = 100,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "examp@le1example")
    private String password;
}
