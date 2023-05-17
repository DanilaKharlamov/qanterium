package com.dkharlamov.qanterium.input.member.repository;

import com.dkharlamov.qanterium.common.entity.BaseEntity;
import com.dkharlamov.qanterium.input.expensecard.repository.ExpenseCard;
import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.security.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity<Long> {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "expense_card.id")
    @JsonBackReference
    private ExpenseCard expenseCard;

    @OneToMany(
            mappedBy = "owner",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private List<Transaction> owner = new ArrayList<>();

    @ManyToMany(
            mappedBy = "debts",
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private List<Transaction> debts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinColumn(name = "user_id")
    private User user;
}
