package com.dkharlamov.qanterium.input.member.controller;

import com.dkharlamov.qanterium.common.constant.SwaggerConstants;
import com.dkharlamov.qanterium.common.dto.ErrorDto;
import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import com.dkharlamov.qanterium.input.member.service.MemberService;
import com.dkharlamov.qanterium.input.member.dto.MemberRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "Service for working with members of the expense card")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/expenseCard/{id}")
    @Operation(summary = "Receive members", description = "Receive all members by expense card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Карточки трат не найдены",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200",
                    description = "Expense card successfully found",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MemberResponse.class, nullable = true)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public List<MemberResponse> getExpenseCardMembers(
            @Parameter(description = "Unique identifier of the expense card")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        return memberService.getMembersByExpenseCardId(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Change expense card member attribute", description = "Change expense card member attribute")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense card not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200", description = "Found expense cards")
    })
    public ResponseEntity<Void> updateExpenseCardMember(
            @Parameter(description = "Unique identifier of the member")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id,
            @RequestBody MemberRequest memberRequest) {
        memberService.updateMember(id, memberRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete expense card member", description = "Delete expense card member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Expense card not found",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            @ApiResponse(responseCode = "200", description = "Found expense card")
    })
    public ResponseEntity<Void> deleteMemberFromExpenseCard(
            @Parameter(description = "Unique identifier of the member")
            @Schema(minimum = SwaggerConstants.ZERO_VALUE, maximum = SwaggerConstants.MAX_SAFE_LONG_VALUE)
            @PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
