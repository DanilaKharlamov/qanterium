package com.dkharlamov.qanterium.input.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(nativeQuery = true, value = "SELECT * from member where member.expense_card_id = ?1")
    List<Member> findByExpenseCardId(Long expenseCardId);

    List<Member> findAllByIdIsNot(Long ownerId);
}
