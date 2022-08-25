package com.eri.configuration.rest;

import com.eri.configuration.rest.handler.RestTemplateResponseErrorHandler;
import com.eri.configuration.rest.oauth2.MovieOAuth2ClientContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Configuration
public class RestTemplateConfig {

    @Resource
    private MovieOAuth2ClientContext movieOAuth2ClientContext;

    @Bean
    @ConfigurationProperties("spring.security.oauth2.client.movie-api")
    protected ClientCredentialsResourceDetails movieApiResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }


    @Bean(name = "movieRestTemplate")
    public RestTemplate movieRestTemplate() {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(movieApiResourceDetails(), movieOAuth2ClientContext);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
        restTemplate.setAccessTokenProvider(provider);
        return restTemplate;
    }
}
