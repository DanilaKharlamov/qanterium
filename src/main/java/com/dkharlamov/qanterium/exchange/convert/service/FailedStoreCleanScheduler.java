package com.dkharlamov.qanterium.exchange.convert.service;

import com.dkharlamov.qanterium.exchange.common.exception.RatesDoesntExistException;
import com.dkharlamov.qanterium.exchange.convert.dto.FailedConversionsStore;
import com.dkharlamov.qanterium.exchange.convert.dto.FailedItem;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FailedStoreCleanScheduler {
    private final ConverterService converterService;
    private final FailedConversionsStore failedConversionsStore;

    @Scheduled(cron = "0 0 */8 ? * *") // run every 8 hours per day
    public void cleanNotCalculatedValues() {
        log.info("Cleaner starting his work at:{}", System.currentTimeMillis());
        cleanNotCalculatedQueue();
        log.info("Cleaner end this work at:{}", System.currentTimeMillis());
    }

    private void cleanNotCalculatedQueue() {
        log.info("Not successfully converted item size:{}", failedConversionsStore.getValues().size());
        if (failedConversionsStore.getValues().isEmpty()) {
            log.info("FailedConversionsStore is empty.");
            return;
        }

        for (Iterator<Map.Entry<Long, FailedItem>> it = failedConversionsStore.getValues().entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, FailedItem> entry = it.next();
            FailedItem item = entry.getValue();
            try {
                manageCleaning(item);
                it.remove();
            } catch (RatesDoesntExistException e) {
                item.setFailCounter(item.getFailCounter() + 1);
                log.warn("Failed to calculate exchange values for transaction {}. {} times in a row",
                        item.getTransactionId(), item.getFailCounter(), e
                );
            }
        }
    }

    private void manageCleaning(FailedItem item) throws RatesDoesntExistException {
        log.info("Item = {}", item);
        converterService.convert(
                item.getBaseValue(),
                item.getBaseCurrency(),
                item.getTransactionId(),
                item.getFailCounter() > 2
        );
    }
}
