package com.dkharlamov.qanterium.input.counter;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.counter.dto.MemberDebtsInfoDto;
import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/count")
@RequiredArgsConstructor
@Tag(name = "Count", description = "Counting expenses and debts of members")
public class CounterController {
    private final CounterService counterService;

    @GetMapping("/member/{id}/debts")
    @Operation(summary = "Calculate the debt for the member", description = "Calculate the debt for the member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Member not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Successfully found member",
                    content = @Content(schema = @Schema(implementation = ExpenseCardResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public MemberDebtsInfoDto getDebtsAmountByMember(
            @Parameter(description = "Unique identifier of the member")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id
    ) {
        return counterService.countDebtsByMemberId(id);
    }
}
