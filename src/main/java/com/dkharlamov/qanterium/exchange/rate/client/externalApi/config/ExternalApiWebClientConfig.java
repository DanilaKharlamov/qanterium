package com.dkharlamov.qanterium.exchange.rate.client.externalApi.config;

import io.netty.handler.ssl.SslContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Data
@Configuration
public class ExternalApiWebClientConfig {

    @Value("${qanterium.exchange.external-api.url}")
    private String baseUrl;

    @Value("${qanterium.exchange.external-api.api-key}")
    private String apiKey;

    @Value("${qanterium.exchange.max-in-memory-size-mb}")
    private int maxSize;

    @Bean("externalApiWebClient")
    public WebClient webClient(@Autowired(required = false) @Qualifier("externalApiSslContext") SslContext sslContext) {
        HttpClient httpClient;
        if (sslContext == null) {
            httpClient = HttpClient.create();
            log.info("WebClient for ExternalApi without SslContext has been successfully formed");
        } else {
            httpClient = HttpClient.create().secure(it -> it.sslContext(sslContext));
            log.info("WebClient for ExternalApi with SslContext has been successfully formed");
        }
        int size = maxSize * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .baseUrl(baseUrl + apiKey)
                .exchangeStrategies(strategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
