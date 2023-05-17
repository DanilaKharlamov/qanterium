package com.dkharlamov.qanterium.exchange.convert.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FailedItem {

    private Double baseValue;
    private String baseCurrency;
    private Long transactionId;
    private int failCounter;
}
