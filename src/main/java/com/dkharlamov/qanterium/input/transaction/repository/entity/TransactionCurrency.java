package com.dkharlamov.qanterium.input.transaction.repository.entity;

import com.dkharlamov.qanterium.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCurrency extends BaseEntity<Integer> {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @OneToMany(
            mappedBy = "transactionCurrency",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<TransactionValue> values = new ArrayList<>();
}
