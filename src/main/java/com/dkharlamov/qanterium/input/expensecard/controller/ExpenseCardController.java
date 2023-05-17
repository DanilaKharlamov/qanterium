package com.dkharlamov.qanterium.input.expensecard.controller;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardRequest;
import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardResponse;
import com.dkharlamov.qanterium.input.member.dto.MemberRequest;
import com.dkharlamov.qanterium.input.expensecard.service.ExpenseCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenseCards")
@RequiredArgsConstructor
@Tag(name = "ExpenseCards", description = "Service for working with the expense card")
public class ExpenseCardController {
    private final ExpenseCardService expenseCardService;

    @GetMapping
    @Operation(summary = "Receive expense cards", description = "Receive all active expense cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense cards were not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Found expense cards",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ExpenseCardResponse.class, nullable = true)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public List<ExpenseCardResponse> getExpenseCards() {
        return expenseCardService.getExpenseCards();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a expense card by id", description = "Get a expense card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense card not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Found expense card",
                    content = @Content(schema = @Schema(implementation = ExpenseCardResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ExpenseCardResponse getExpenseCard(
            @Parameter(description = "Unique identifier of the expense card")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        return expenseCardService.getExpenseCard(id);
    }

    @PostMapping
    @Operation(summary = "Create new expense card", description = "Create new expense card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Failed to create a spending card",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Created expenses card",
                    content = @Content(schema = @Schema(implementation = ExpenseCardResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ExpenseCardResponse createExpenseCard(@RequestBody ExpenseCardRequest expenseCardRequest, @AuthenticationPrincipal UserDetails userDetails) {
        return expenseCardService.createExpenseCard(expenseCardRequest,userDetails.getUsername());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Change expense card attribute", description = "Change expense card attribute")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense card not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Successfully modified expense card",
                    content = @Content(schema = @Schema(implementation = ExpenseCardResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ExpenseCardResponse updateExpenseCard(
            @Parameter(description = "Unique identifier of the spending card")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id,
            @RequestBody ExpenseCardRequest expenseCardRequest) {
        return expenseCardService.updateExpenseCard(id, expenseCardRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete active expense card", description = "Delete active expense card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense card not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "204", description = "Deleted expense card")
    })
    public ResponseEntity<Void> deleteExpenseCard(
            @Parameter(description = "Unique identifier of the expense card")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        expenseCardService.deleteExpenseCard(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members")
    @Operation(summary = "Add members to the expense card",
            description = "Add members to the expense card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense card not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "204", description = "Successful addition of a members")
    })
    public ResponseEntity<Void> addMemberToExpenseCard(
            @Parameter(description = "Unique identifier of the expense card")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id,
            @RequestBody MemberRequest memberRequest) {
        expenseCardService.addMemberToExpenseCard(id, memberRequest);
        return ResponseEntity.noContent().build();
    }
}
