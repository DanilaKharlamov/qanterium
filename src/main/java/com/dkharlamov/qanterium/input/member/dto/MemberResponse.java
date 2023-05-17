package com.dkharlamov.qanterium.input.member.dto;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(
        title = "Member",
        description = "Response member with attribute"
)
public class MemberResponse {
    @Schema(description = "Unique identifier",
            minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE,
            pattern = SwaggerConstants.DIGITS_ONLY_PATTERN, example = "154")
    private Long id;

    @Schema(description = "Name of the member",
            minLength = SwaggerConstants.MIN_STRING_LENGTH_1, maxLength = SwaggerConstants.MAX_STRING_LENGTH_255,
            pattern = SwaggerConstants.ANY_SYMBOL_PATTERN, example = "Carl")
    private String name;
}
