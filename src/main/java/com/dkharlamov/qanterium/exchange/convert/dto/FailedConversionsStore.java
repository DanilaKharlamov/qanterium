package com.dkharlamov.qanterium.exchange.convert.dto;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class FailedConversionsStore {
    private final ConcurrentHashMap<Long, FailedItem> values = new ConcurrentHashMap<>();
}
