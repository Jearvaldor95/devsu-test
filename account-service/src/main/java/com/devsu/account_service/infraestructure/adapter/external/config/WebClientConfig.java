package com.devsu.account_service.infraestructure.adapter.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    String url = "http://localhost:8095/api";
    @Bean
    WebClient webClient(){
        return WebClient.builder().baseUrl(url).build();
    }
}
