package com.dkharlamov.qanterium.input.transaction.util;

import com.dkharlamov.qanterium.input.transaction.exception.TransactionException;
import com.dkharlamov.qanterium.input.transaction.repository.TransactionCurrencyRepository;
import com.dkharlamov.qanterium.input.transaction.repository.TransactionRepository;
import com.dkharlamov.qanterium.input.transaction.repository.TransactionValueRepository;
import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.dkharlamov.qanterium.common.constant.ExceptionConstants.notFound;

@Getter
@Component
@RequiredArgsConstructor
public class TransactionProvider {
    private final TransactionRepository transactionRepository;
    private final TransactionValueRepository transactionValueRepository;
    private final TransactionCurrencyRepository transactionCurrencyRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionException(String.format(notFound, "transaction", transactionId)));
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public List<TransactionValue> getTransactionValuesByTransactionId(Long transactionId) {
        return transactionValueRepository.findByTransactionId(transactionId);
    }

    public TransactionCurrency getTransactionCurrencyByCode(String currencyCode) {
        return transactionCurrencyRepository.findByCode(currencyCode.toUpperCase());
    }

    public List<TransactionCurrency> getExceptBaseCurrency(List<TransactionCurrency> transactionCurrencies, String baseCurrencyCode) {
        return transactionCurrencies.stream()
                .filter(currency -> !currency.getCode().equals(baseCurrencyCode.toUpperCase()))
                .collect(Collectors.toList());
    }

    public TransactionCurrency getBaseTransactionCurrency(List<TransactionCurrency> transactionCurrencies, String baseCurrencyCode) {
        return transactionCurrencies.stream()
                .filter(currency -> currency.getCode().equals(baseCurrencyCode.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new TransactionException(String.format(notFound, "currency code", baseCurrencyCode)));
    }

    public List<TransactionCurrency> getCurrencies() {
        return transactionCurrencyRepository.findAll();
    }
}
