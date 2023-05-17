package com.dkharlamov.qanterium.exchange.rate.dto;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
public class NbgCurrencyRate {
    private String code;
    private Integer quantity;
    private String rateFormated;
    private String diffFormated;
    private Double rate;
    private String name;
    private Double diff;
    private String date;
    private String validFromDate;
}
