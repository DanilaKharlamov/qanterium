package com.dkharlamov.qanterium.input.transaction.repository;

import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCurrencyRepository extends JpaRepository<TransactionCurrency, Integer> {
    TransactionCurrency findByCode(String code);
}
