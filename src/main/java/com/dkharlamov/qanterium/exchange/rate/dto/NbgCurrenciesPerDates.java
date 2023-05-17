package com.dkharlamov.qanterium.exchange.rate.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class NbgCurrenciesPerDates {
    private String date;
    private List<NbgCurrencyRate> currencies = new ArrayList<>();
}
