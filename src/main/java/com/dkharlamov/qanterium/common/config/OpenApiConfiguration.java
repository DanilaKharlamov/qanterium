package com.dkharlamov.qanterium.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openAPI(SwaggerProperties swaggerProperties) {
        return swaggerProperties;
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            Components components = openApi.getComponents();
            if (components != null) {
                Map<String, Schema> schemas = components.getSchemas();
                if (schemas != null) {
                    schemas.values().forEach(s -> s.setAdditionalProperties(false));
                }
            }
        };
    }
}
