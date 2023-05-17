package com.dkharlamov.qanterium.input.transaction.repository.entity;

import com.dkharlamov.qanterium.common.entity.BaseEntity;
import com.dkharlamov.qanterium.input.member.repository.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Transaction extends BaseEntity<Long> {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Column(name = "transaction_value", nullable = false)
    @OrderBy("id ASC")
    private List<TransactionValue> transactionValue = new ArrayList<>();

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private Member owner;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "xref_transaction_to_member_debts",
            joinColumns = {@JoinColumn(name = "transaction_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "id")}
    )
    @JsonManagedReference
    private Set<Member> debts = new HashSet<>();

    public void addDebts(Member member) {
        this.debts.add(member);
        member.getDebts().add(this);
    }

    public void addTransactionValue(TransactionValue transactionValue) {
        if (CollectionUtils.isEmpty(this.transactionValue))
            this.transactionValue = new ArrayList<>();

        this.transactionValue.add(transactionValue);
        transactionValue.setTransaction(this);
    }

    public void updateTransactionValue(String currency, Double value) {
        BigDecimal newValue = BigDecimal.valueOf(value);
        this.getTransactionValue().stream()
                .filter(tv -> tv.getTransactionCurrency().getCode().equals(currency))
                .findFirst()
                .ifPresent(tv -> tv.setValue(newValue));
    }

    public static class Builder {
        private final Transaction transaction;

        public Builder() {
            this.transaction = new Transaction();
        }

        public Builder id(Long id) {
            if (id != null) {
                this.transaction.id = id;
            }
            return this;
        }

        public Builder name(String name) {
            if (StringUtils.isNoneEmpty(name)) {
                this.transaction.setName(name);
            }
            return this;
        }

        public Builder description(String description) {
            if (StringUtils.isNoneEmpty(description)) {
                this.transaction.setDescription(description);
            }
            return this;
        }

        public Builder createdAt(LocalDate createdAt) {
            if (createdAt != null) {
                this.transaction.createdAt = createdAt;
            }
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            if (updatedAt != null) {
                this.transaction.updatedAt = updatedAt;
            }
            return this;
        }

        public Builder owner(Member owner) {
            if (owner != null) {
                this.transaction.setOwner(owner);
            }
            return this;
        }

        public Builder debts(Set<Member> debtsMembers) {
            if (!debtsMembers.isEmpty()) {
                this.transaction.getDebts().addAll(debtsMembers);
            }
            return this;
        }

        public Builder transactionValue(List<TransactionValue> transactionValueList) {
            if (!CollectionUtils.isEmpty(transactionValueList)) {

                transactionValueList.forEach(transactionValue -> {
                    this.transaction.transactionValue.add(transactionValue);
                    transactionValue.setTransaction(this.transaction);
                });
            }
            return this;
        }

        public Transaction build() {
            return this.transaction;
        }
    }
}
