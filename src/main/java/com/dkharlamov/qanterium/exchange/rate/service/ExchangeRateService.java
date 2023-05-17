package com.dkharlamov.qanterium.exchange.rate.service;

import com.dkharlamov.qanterium.exchange.rate.client.externalApi.ExternalApiClient;
import com.dkharlamov.qanterium.exchange.rate.client.nbg.NbgClient;
import com.dkharlamov.qanterium.exchange.rate.dto.ConversionRates;
import com.dkharlamov.qanterium.exchange.rate.repository.ExchangePair;
import com.dkharlamov.qanterium.exchange.rate.util.ExchangeRateProvider;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.util.TransactionProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
@Component
public final class ExchangeRateService {
    private final NbgClient nbgClient;
    private final ExternalApiClient externalApiClient;
    private final ExchangeRateProvider exchangeRateProvider;
    private final TransactionProvider transactionProvider;

    public void updateDailyExchangePairs() {
        List<TransactionCurrency> supportedCurrencies = transactionProvider.getCurrencies();

        for (TransactionCurrency baseCurrency : supportedCurrencies) {
            ConversionRates externalConversionRates = externalApiClient.getExchangeRates(baseCurrency.getCode());
            List<ExchangePair> exchangePairs = createExchangePairs(baseCurrency, supportedCurrencies, externalConversionRates);
            exchangeRateProvider.save(exchangePairs);
        }
    }

    private List<ExchangePair> createExchangePairs(
            TransactionCurrency baseCurrency,
            List<TransactionCurrency> supportedCurrencies,
            ConversionRates conversionRates
    ) {
        Map<String, Double> externalConversionRatesMap = Map.of(
                "USD", conversionRates.getUsd(),
                "EUR", conversionRates.getEur(),
                "RUB", conversionRates.getRub(),
                "GEL", conversionRates.getGel()
        );

        return supportedCurrencies.stream()
                .filter(currency -> !currency.equals(baseCurrency))
                .map(currency -> {
                    BigDecimal value = BigDecimal.valueOf(externalConversionRatesMap.get(currency.getCode()));
                    return buildExchangePair(baseCurrency, currency, value);
                })
                .collect(Collectors.toList());
    }

    private ExchangePair buildExchangePair(TransactionCurrency baseCurrency, TransactionCurrency relatedCurrency, BigDecimal value) {
        return ExchangePair.builder()
                .baseCurrency(baseCurrency)
                .relatedCurrency(relatedCurrency)
                .value(value)
                .createdByUser(false)
                .build();
    }
}
