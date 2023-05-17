package com.dkharlamov.qanterium.input.expensecard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCardRepository extends JpaRepository<ExpenseCard, Long> {

}
