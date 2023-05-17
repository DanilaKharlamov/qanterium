package com.dkharlamov.qanterium.input.counter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DebtValueDto {

    private String currency;
    private BigDecimal value;
}
