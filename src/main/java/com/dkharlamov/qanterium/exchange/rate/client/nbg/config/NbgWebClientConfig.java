package com.dkharlamov.qanterium.exchange.rate.client.nbg.config;

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
public class NbgWebClientConfig {

    @Value("${qanterium.exchange.nbg.url}")
    private String baseUrl;
    @Value("${qqanterium.exchange.max-in-memory-size-mb}")
    private int maxSize;

    @Bean("nbgWebClient")
    public WebClient webClient(@Autowired(required = false) @Qualifier("nbgSslContext") SslContext sslContext) {
        HttpClient httpClient;
        if (sslContext == null) {
            httpClient = HttpClient.create();
            log.info("WebClient for NBG without SslContext has been successfully formed");
        } else {
            httpClient = HttpClient.create().secure(it -> it.sslContext(sslContext));
            log.info("WebClient for NBG with SslContext has been successfully formed");
        }
        int size = maxSize * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .baseUrl(baseUrl)
                .exchangeStrategies(strategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
