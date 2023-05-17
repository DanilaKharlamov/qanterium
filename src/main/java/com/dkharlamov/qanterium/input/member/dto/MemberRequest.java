package com.dkharlamov.qanterium.input.member.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(
        title = "Expense card members",
        description = "Request to add new members to an existing expense card"
)
public class MemberRequest {
    @ArraySchema(arraySchema = @Schema(description = "Names or nicknames of the members to be added to the card",
            nullable = true, example = "Carl"),
            maxItems = 200)
    private List<String> names;
}
