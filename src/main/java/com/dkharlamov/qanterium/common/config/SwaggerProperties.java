package com.dkharlamov.qanterium.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger.properties")
public class SwaggerProperties extends OpenAPI {
    public SwaggerProperties() {
        info(new Info()
                .title("title")
                .version("1.0.0")
                .description("description")
                .contact(new Contact()
                        .email("email@email.com")
                        .name("name")
                        .url("url.com")
                )
        );
    }
}
