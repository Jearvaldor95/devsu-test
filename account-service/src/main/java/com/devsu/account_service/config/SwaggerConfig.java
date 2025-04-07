package com.devsu.account_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Account service")
                        .version("1.0")
                        .description("Service for account management implementing clean architecture.")
                        .termsOfService("https://github.com/Jearvaldor95/devsu-test")
                        .license(new License().name("Apache 2.8.6").url("http://springdoc.org"))
                );
    }
}
