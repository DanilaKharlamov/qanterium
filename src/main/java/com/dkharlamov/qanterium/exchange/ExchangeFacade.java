package com.dkharlamov.qanterium.exchange;

import com.dkharlamov.qanterium.exchange.convert.service.ConverterService;
import com.dkharlamov.qanterium.exchange.common.exception.RatesDoesntExistException;
import com.dkharlamov.qanterium.exchange.rate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public final class ExchangeFacade {
    private final ExchangeRateService exchangeRateService;
    private final ConverterService converterService;

    public void convertValueToOtherCurrencies(Double baseValue, String baseCurrency, Long transactionId, boolean isFirstTime) {
        try {
            converterService.convert(baseValue, baseCurrency, transactionId, isFirstTime);
            log.info("Successfully received and stored currencies values. Base Currency:{}", baseCurrency);
        } catch (RatesDoesntExistException e) {
            log.info("Failed while truing to received and stored currencies values. Base Currency:{}", baseCurrency);
            exchangeRateService.updateDailyExchangePairs();
        }
    }
}
