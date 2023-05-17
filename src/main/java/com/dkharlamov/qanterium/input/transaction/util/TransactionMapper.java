package com.dkharlamov.qanterium.input.transaction.util;

import com.dkharlamov.qanterium.input.transaction.dto.TransactionResponse;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionValueResponse;
import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import com.dkharlamov.qanterium.input.member.util.MemberMapper;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionMapper {
    public TransactionMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TransactionResponse transactionEntityToDto(Transaction transaction) {
        String description = Objects.isNull(transaction.getDescription()) ? null : transaction.getDescription();
        return TransactionResponse.builder()
                .id(transaction.getId())
                .name(transaction.getName())
                .description(description)
                .transactionValueResponse(transaction.getTransactionValue().stream().map(TransactionMapper::valueEntityToDto).toList())
                .owner(MemberMapper.entityToDto(transaction.getOwner()))
                .debts(transaction.getDebts().stream().map(MemberMapper::entityToDto).toList())
                .createdBy(transaction.getCreatedAt().toString())
                .build();
    }

    public static TransactionValueResponse valueEntityToDto(TransactionValue transactionValue) {
        BigDecimal exchangeRate = Objects.isNull(transactionValue.getExchangePair())
                ? BigDecimal.valueOf(1)
                : transactionValue.getExchangePair().getValue();

        return TransactionValueResponse.builder()
                .value(transactionValue.getValue())
                .currencyCode(transactionValue.getTransactionCurrency().getCode())
                .currencyName(transactionValue.getTransactionCurrency().getFullName())
                .isBaseCurrency(transactionValue.getIsBase())
                .exchangeRate(exchangeRate)
                .build();
    }
}
