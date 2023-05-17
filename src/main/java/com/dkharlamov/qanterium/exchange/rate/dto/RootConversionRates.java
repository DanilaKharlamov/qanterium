package com.dkharlamov.qanterium.exchange.rate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RootConversionRates {

    public String result;
    @JsonProperty("time_last_update_utc")
    public String timeLastUpdateUtc;
    @JsonProperty("time_next_update_utc")
    public String timeNextUpdateUtc;
    @JsonProperty("base_code")
    public String baseCode;
    @JsonProperty("conversion_rates")
    public ConversionRates conversionRates;

}
