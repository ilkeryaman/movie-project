package com.eri.configuration.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean(name = "movieRestTemplate")
    public RestTemplate movieRestTemplate() {
        return new RestTemplate();
    }
}
