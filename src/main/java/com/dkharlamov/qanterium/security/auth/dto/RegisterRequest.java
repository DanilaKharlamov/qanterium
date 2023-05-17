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
@Schema(title = "Registration", description = "User registration request")
public class RegisterRequest {
    @Schema(description = "Username/login",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = 25,
            pattern = SwaggerConstants.ALPHANUMERIC_PATTERN, example = "exampleusername")
    private String username;

    @Schema(description = "Email",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = 100,
            pattern = SwaggerConstants.EMAIL_PATTERN, example = "example@gmail.com")
    private String email;

    @Schema(description = "Password",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = 100,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "examp@le1example")
    private String password;

    @Schema(description = "User firstname", nullable = true, maxLength = 25,
            pattern = SwaggerConstants.LETTERS_ONLY_PATTERN, example = "Firstname")
    private String firstname;

    @Schema(description = "User lastname", nullable = true, maxLength = 40,
            pattern = SwaggerConstants.LETTERS_ONLY_PATTERN, example = "Lastname")
    private String lastname;
}
