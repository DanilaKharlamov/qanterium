package com.dkharlamov.qanterium.exchange.rate.client.nbg.config;

import com.dkharlamov.qanterium.exchange.common.exception.ExchangeException;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({NbgSecureProperties.class})
public class NbgSslContextConfig {
    private final NbgSecureProperties secureProps;

    @SneakyThrows
    @Bean("nbgSslContext")
    public SslContext sslSocket() {
        SslContextBuilder sslContextBuilder = SslContextBuilder.forClient().protocols(secureProps.getProtocol());

        if (Strings.isNotBlank(secureProps.getTrustStorePath())) {
            KeyStore truststore = getTrustStore(secureProps.getTrustStorePath(), secureProps.getTrustStorePassword());
            TrustManagerFactory trustManagerFactory = getTrustManagerFactory(truststore);
            sslContextBuilder.trustManager(trustManagerFactory);

            if (secureProps.isMutualEnabled() && Strings.isNotBlank(secureProps.getKeyStorePath())) {
                KeyStore keyStore = getTrustStore(secureProps.getKeyStorePath(), secureProps.getKeyStorePassword());
                KeyManagerFactory keyManagerFactory = getKeyManagerFactory(keyStore, secureProps.getSecretKeyPassword());
                sslContextBuilder.keyManager(keyManagerFactory);
            }
        }
        return sslContextBuilder.build();
    }

    private KeyStore getTrustStore(String trustStorePath, char[] password) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ResourceUtils.getFile(trustStorePath), password);
            log.info("NBG SSL certificate downloaded successfully");
            return keyStore;
        } catch (Exception e) {
            throw new ExchangeException("Failed to create trustStore from NBG certificate. Msg: %s", e.getMessage());
        }
    }

    @SneakyThrows
    private TrustManagerFactory getTrustManagerFactory(KeyStore keyStore) {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        return trustManagerFactory;
    }

    @SneakyThrows
    private KeyManagerFactory getKeyManagerFactory(KeyStore keyStore, char[] secretKey) {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, secretKey);
        return keyManagerFactory;
    }
}
