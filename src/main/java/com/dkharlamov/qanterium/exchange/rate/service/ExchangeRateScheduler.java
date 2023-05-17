package com.dkharlamov.qanterium.exchange.rate.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExchangeRateScheduler {
    private final ExchangeRateService exchangeRateService;

    @Scheduled(cron = "0 0 21 ? * MON-FRI") // run every day in 9 p.m. except weekend hours
    public void updateDailyExchangePairs() {
        log.info("Daily rate updater scheduler starting work at:{}", System.currentTimeMillis());
        exchangeRateService.updateDailyExchangePairs();
        log.info("Daily rate updater scheduler end the work at:{}", System.currentTimeMillis());
    }
}
