package com.dkharlamov.qanterium.exchange.rate.util;

import com.dkharlamov.qanterium.exchange.rate.repository.ExchangePair;
import com.dkharlamov.qanterium.exchange.rate.repository.ExchangePairRepository;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateProvider {
    private final ExchangePairRepository exchangePairRepository;

    public List<ExchangePair> getExchangePairsByDate(TransactionCurrency transactionCurrency, LocalDate date) {
        return exchangePairRepository.findByBaseCurrencyAndCreatedAt(transactionCurrency, date);
    }

    public List<ExchangePair> getLastExchangePairs(TransactionCurrency transactionCurrency) {
        return exchangePairRepository.findLastByBaseCurrency(transactionCurrency.getId());
    }

    public void save(List<ExchangePair> exchangePairs) {
        log.info("Persisting {} exchange rates.", exchangePairs.size());
        exchangePairRepository.saveAll(exchangePairs);
    }
}
