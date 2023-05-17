package com.dkharlamov.qanterium.input.transaction.service.impl;

import com.dkharlamov.qanterium.exchange.rate.repository.ExchangePair;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionValueService {
    public TransactionValue buildValue(BigDecimal value, TransactionCurrency currency, boolean isBase, ExchangePair exchangePair) {
        return TransactionValue.builder()
                .value(value)
                .isBase(isBase)
                .transactionCurrency(currency)
                .exchangePair(exchangePair)
                .build();
    }

    public List<TransactionValue> reCalculateTransactionValues(List<TransactionValue> transactionValues, Double baseValue) {
        transactionValues.stream()
                .filter(TransactionValue::getIsBase)
                .forEach(it -> it.setValue(BigDecimal.valueOf(baseValue)));
        transactionValues.stream()
                .filter(it -> !it.getIsBase())
                .forEach(it -> it.setValue(BigDecimal.valueOf(baseValue).multiply(it.getExchangePair().getValue())));
        return transactionValues;
    }
}
