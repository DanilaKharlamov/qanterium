package com.dkharlamov.qanterium.exchange.rate.client.externalApi;

import com.dkharlamov.qanterium.exchange.rate.dto.ConversionRates;
import com.dkharlamov.qanterium.exchange.rate.dto.RootConversionRates;
import com.dkharlamov.qanterium.exchange.common.exception.ExchangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class ExternalApiClient {
    private final WebClient webClient;

    public ExternalApiClient(@Qualifier("externalApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public ConversionRates getExchangeRates(String currency) {
        return webClient.get()
                .uri("/latest/{currency}", currency)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(RootConversionRates.class)
                .map(RootConversionRates::getConversionRates)
                .doOnSuccess(it -> log.info("Currencies date list was successfully download from External Api "))
                .block();
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .map(body -> {
                    log.warn("Couldn't download currencies rate from External Api. Status: {}. Error: {}", response.statusCode().value(), body);
                    throw new ExchangeException(response.statusCode().toString());
                });
    }

}
