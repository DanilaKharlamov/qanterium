package com.dkharlamov.qanterium.input.transaction.repository.entity;

import com.dkharlamov.qanterium.common.entity.BaseEntity;
import com.dkharlamov.qanterium.exchange.rate.repository.ExchangePair;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionValue extends BaseEntity<Long> {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "transaction_id")
    @JsonIgnore
    private Transaction transaction;

    @ManyToOne(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "transaction_currency_id")
    private TransactionCurrency transactionCurrency;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "exchange_pair_id")
    private ExchangePair exchangePair;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "is_base", nullable = false, updatable = false)
    private Boolean isBase;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
