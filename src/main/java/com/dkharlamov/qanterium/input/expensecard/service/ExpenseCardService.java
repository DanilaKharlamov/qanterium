package com.dkharlamov.qanterium.input.expensecard.service;

import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardRequest;
import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardResponse;
import com.dkharlamov.qanterium.input.member.dto.MemberRequest;

import java.util.List;

public interface ExpenseCardService {

    List<ExpenseCardResponse> getExpenseCards();

    ExpenseCardResponse getExpenseCard(Long id);

    ExpenseCardResponse createExpenseCard(ExpenseCardRequest expenseCardRequest, String username);

    ExpenseCardResponse updateExpenseCard(Long expenseCardId, ExpenseCardRequest expenseCardRequest);

    void deleteExpenseCard(Long expenseCardId);

    void addMemberToExpenseCard(Long expenseCardId, MemberRequest memberRequest);

}
