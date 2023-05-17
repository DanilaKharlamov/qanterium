package com.dkharlamov.qanterium.input.transaction.controller;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionResponse;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionRequest;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionValueResponse;
import com.dkharlamov.qanterium.input.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Service for working with transaction components")
public class TransactionController {
    private final TransactionService transactionService;

    @Hidden
    @GetMapping
    public List<TransactionResponse> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a transaction by id", description = "Get a transaction by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found",
                    content = @Content(schema = @Schema(implementation = TransactionResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public TransactionResponse getTransaction(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping("/members/{id}")
    @Operation(summary = "Get a transactions for member by id", description = "Get a transactions for member by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found",
                    content = @Content(array = @ArraySchema(
                            schema = @Schema(implementation = TransactionResponse.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public List<TransactionResponse> getTransactionsForMember(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        return transactionService.getTransactionsForMember(id);
    }

    @GetMapping("/members/{id}/debts")
    @Operation(summary = "Get a debt transactions for member by id", description = "Get a debt transactions for member by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public List<TransactionResponse> getDebtTransactionsForMember(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        return transactionService.getDebtTransactionsForMember(id);
    }

    @GetMapping("/members/{id}/expenses")
    @Operation(summary = "Get a expense transactions for member by id",
            description = "Get a expense transactions for member by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public List<TransactionResponse> getExpenseTransactionsForMember(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        return transactionService.getExpenseTransactionsForMember(id);
    }

    @PostMapping
    @Operation(summary = "Create new transaction",
            description = "Create new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction was successfully created"
            )
    })
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.createTransaction(transactionRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Change transaction attribute",
            description = "Change transaction attribute")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Successful updated"

            )
    })
    public ResponseEntity<Void> updateTransaction(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id,
            @RequestBody TransactionRequest transactionRequest) {
        transactionService.updateTransaction(transactionRequest, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction",
            description = "Delete transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found"
            )
    })
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id
    ) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/transactionValues")
    @Operation(summary = "Receive transaction",
            description = "Receive transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public List<TransactionValueResponse> getValuesForTransaction(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id
    ) {
        return transactionService.getTransactionValues(id);
    }

    @DeleteMapping("/{transactionId}/member{memberId}")
    @Operation(summary = "Remove member from the transaction debts",
            description = "Remove member from the transaction debts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Transaction not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Transaction found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public ResponseEntity<Void> removeMemberFromTransactionDebts(
            @Parameter(description = "Unique identifier of the transaction")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long transactionId,
            @Parameter(description = "Unique identifier of the member")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long memberId) {
        transactionService.removeDebtFromTransaction(transactionId, memberId);
        return ResponseEntity.noContent().build();
    }
}
