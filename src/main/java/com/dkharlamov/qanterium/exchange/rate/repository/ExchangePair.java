package com.dkharlamov.qanterium.exchange.rate.repository;

import com.dkharlamov.qanterium.common.entity.BaseEntity;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangePair extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "base_currency_id", nullable = false)
    private TransactionCurrency baseCurrency;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "related_currency_id", nullable = false)
    private TransactionCurrency relatedCurrency;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @BooleanFlag
    @Column(name = "created_by_user", updatable = false)
    private Boolean createdByUser = false;

    @OneToMany(
            mappedBy = "exchangePair",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TransactionValue> transactionValues = new ArrayList<>();
}
