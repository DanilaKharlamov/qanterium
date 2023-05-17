package com.dkharlamov.qanterium.exchange.convert.service;

import com.dkharlamov.qanterium.exchange.convert.dto.FailedConversionsStore;
import com.dkharlamov.qanterium.exchange.rate.repository.ExchangePair;
import com.dkharlamov.qanterium.exchange.common.exception.ExchangeException;
import com.dkharlamov.qanterium.exchange.common.exception.RatesDoesntExistException;
import com.dkharlamov.qanterium.exchange.convert.dto.FailedItem;
import com.dkharlamov.qanterium.exchange.rate.util.ExchangeRateProvider;
import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import com.dkharlamov.qanterium.input.transaction.util.TransactionProvider;
import com.dkharlamov.qanterium.input.transaction.service.impl.TransactionValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConverterService {
    private final ExchangeRateProvider exchangeRateProvider;
    private final TransactionProvider transactionProvider;
    private final FailedConversionsStore failedConversionsStore;
    private final TransactionValueService transactionValueService;

    @Transactional
    public void convert(Double baseValue, String baseCurrencyCode, Long transactionId, boolean isFirstTime) {
        Transaction transaction = transactionProvider.getTransactionById(transactionId);
        List<TransactionCurrency> supportedCurrencies = transactionProvider.getCurrencies();
        TransactionCurrency baseCurrency = transactionProvider.getBaseTransactionCurrency(supportedCurrencies, baseCurrencyCode);
        List<TransactionCurrency> currenciesExceptBase = transactionProvider.getExceptBaseCurrency(supportedCurrencies, baseCurrencyCode);

        List<ExchangePair> exchangePairs = isFirstTime ?
                exchangeRateProvider.getLastExchangePairs(baseCurrency) :
                exchangeRateProvider.getExchangePairsByDate(baseCurrency, transaction.getCreatedAt());

        if (exchangePairs.isEmpty()) {
            addToFailedStore(baseValue, baseCurrencyCode, transactionId);
        }

        List<TransactionValue> transactionValues = currenciesExceptBase.parallelStream()
                .map(currency -> getExchangePairForCurrency(currency, exchangePairs))
                .map(exchangePair -> transactionValueService.buildValue(
                                calculateAmount(baseValue, exchangePair.getValue()),
                                exchangePair.getRelatedCurrency(),
                                false,
                                exchangePair
                        )
                )
                .toList();
        transactionValues.forEach(transaction::addTransactionValue);

        transactionProvider.save(transaction);
    }

    private ExchangePair getExchangePairForCurrency(TransactionCurrency currency, List<ExchangePair> exchangePairs) {
        String currencyCode = currency.getCode().toUpperCase();
        return exchangePairs.stream()
                .filter(rate -> rate.getRelatedCurrency().getCode().equals(currencyCode))
                .findFirst()
                .orElseThrow(() -> new ExchangeException(String.format("Exchange rate not found for currency code: %s", currencyCode)));
    }

    private void addToFailedStore(Double baseValue, String baseCurrency, Long transactionId) {
        failedConversionsStore.getValues().putIfAbsent(
                transactionId,
                FailedItem.builder()
                        .baseValue(baseValue)
                        .baseCurrency(baseCurrency)
                        .transactionId(transactionId)
                        .failCounter(0)
                        .build()
        );
        throw new RatesDoesntExistException(
                String.format("Rates doesn't exist by current currency:%s", baseCurrency));
    }

    private BigDecimal calculateAmount(Double baseValue, BigDecimal rate) {
        return BigDecimal.valueOf(baseValue).multiply(rate);
    }
}
