package com.dkharlamov.qanterium.input.expensecard.util;

import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardResponse;
import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import com.dkharlamov.qanterium.input.expensecard.repository.ExpenseCard;
import com.dkharlamov.qanterium.input.member.util.MemberMapper;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class ExpenseCardMapper {
    public ExpenseCardMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ExpenseCardResponse entityToDto(ExpenseCard expenseCard) {
        List<MemberResponse> memberResponses = !CollectionUtils.isEmpty(expenseCard.getMembers()) ?
                expenseCard.getMembers().stream().map(MemberMapper::entityToDto).toList() :
                Collections.emptyList();

        return ExpenseCardResponse.builder()
                .id(expenseCard.getId())
                .name(expenseCard.getName())
                .description(expenseCard.getDescription())
                .createdBy(expenseCard.getCreatedAt().toString())
                .updatedBy(expenseCard.getUpdatedAt().toString())
                .members(memberResponses)
                .build();
    }
}
