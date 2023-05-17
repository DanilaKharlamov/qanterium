package com.dkharlamov.qanterium.input.transaction.repository;

import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
