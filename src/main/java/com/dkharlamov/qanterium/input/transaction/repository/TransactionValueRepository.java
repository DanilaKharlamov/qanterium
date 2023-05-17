package com.dkharlamov.qanterium.input.transaction.repository;

import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionValueRepository extends JpaRepository<TransactionValue, Long> {

    List<TransactionValue> findByTransactionId(Long id);

    void deleteAllByTransaction(Transaction transaction);
}
