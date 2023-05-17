package com.dkharlamov.qanterium.exchange.rate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ConversionRates{
    @JsonProperty("USD")
    private double usd;
    @JsonProperty("RUB")
    private double rub;
    @JsonProperty("EUR")
    private double eur;
    @JsonProperty("GEL")
    private double gel;
}
