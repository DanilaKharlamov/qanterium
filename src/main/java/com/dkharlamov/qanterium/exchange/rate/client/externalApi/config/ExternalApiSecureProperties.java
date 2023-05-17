package com.dkharlamov.qanterium.exchange.rate.client.externalApi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "qanterium.exchange.external-api")
public class ExternalApiSecureProperties {

    @Value("${qanterium.exchange.external-api.ssl.trust-store.path}")
    private String trustStorePath;
    @Value("${qanterium.exchange.external-api.ssl.trust-store.password}")
    private char[] trustStorePassword;
    @Value("${qanterium.exchange.external-api.protocol}")
    private String protocol;
    @Value("${qanterium.exchange.external-api.ssl.mutual.enabled}")
    private boolean mutualEnabled;
    @Value("${qanterium.exchange.external-api.ssl.mutual.key-store.path}")
    private String keyStorePath;
    @Value("${qanterium.exchange.external-api.ssl.mutual.key-store.password}")
    private char[] keyStorePassword;
    @Value("${qanterium.exchange.external-api.ssl.mutual.key-store.secret-key}")
    private char[] secretKeyPassword;

}
