package com.dkharlamov.qanterium.exchange.rate.client.nbg.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "nbg")
public class NbgSecureProperties {

    @Value("${qanterium.exchange.nbg.ssl.trust-store.path}")
    private String trustStorePath;
    @Value("${qanterium.exchange.nbg.ssl.trust-store.password}")
    private char[] trustStorePassword;
    @Value("${qanterium.exchange.nbg.protocol}")
    private String protocol;
    @Value("${qanterium.exchange.nbg.ssl.mutual.enabled}")
    private boolean mutualEnabled;
    @Value("${qanterium.exchange.nbg.ssl.mutual.key-store.path}")
    private String keyStorePath;
    @Value("${qanterium.exchange.nbg.ssl.mutual.key-store.password}")
    private char[] keyStorePassword;
    @Value("${qanterium.exchange.nbg.ssl.mutual.key-store.secret-key}")
    private char[] secretKeyPassword;
}
