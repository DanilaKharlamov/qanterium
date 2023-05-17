package com.dkharlamov.qanterium.input.transaction.service;

import com.dkharlamov.qanterium.input.transaction.dto.TransactionResponse;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionRequest;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionValueResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getTransactions();

    TransactionResponse getTransaction(Long transactionId);

    List<TransactionResponse> getTransactionsForMember(Long memberId);

    void createTransaction(TransactionRequest request);

    void updateTransaction(TransactionRequest request, Long id);

    void deleteTransaction(Long transactionId);

    List<TransactionResponse> getExpenseTransactionsForMember(Long memberId);

    List<TransactionResponse> getDebtTransactionsForMember(Long memberId);

    List<TransactionValueResponse> getTransactionValues(Long transactionId);

    void removeDebtFromTransaction(Long transactionId, Long memberId);
}
