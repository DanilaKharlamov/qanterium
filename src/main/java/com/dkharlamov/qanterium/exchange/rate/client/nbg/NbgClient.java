package com.dkharlamov.qanterium.exchange.rate.client.nbg;

import com.dkharlamov.qanterium.exchange.rate.dto.NbgCurrenciesPerDates;
import com.dkharlamov.qanterium.exchange.common.exception.ExchangeException;
import com.dkharlamov.qanterium.exchange.rate.dto.NbgCurrencyRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Configuration
public class NbgClient {
    private final WebClient webClient;

    public NbgClient( @Qualifier("nbgWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<NbgCurrencyRate> getExchangeRates(String date, List<String> transactionCurrencies) {
        String uri = UriComponentsBuilder
                .fromPath("/currencies/")
                .queryParam("currencies", transactionCurrencies)
                .queryParam("date", date)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(NbgCurrenciesPerDates[].class)
                .map(currencies -> currencies[0].getCurrencies())
                .doOnSuccess(currencies -> log.info("Currencies date list was successfully download from NBG "))
                .block();
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .map(body -> {
                    log.warn("Couldn't download currencies rate from NBG. Status: {}. Error: {}", response.statusCode().value(), body);
                    throw new ExchangeException(response.statusCode().toString());
                });
    }
}
