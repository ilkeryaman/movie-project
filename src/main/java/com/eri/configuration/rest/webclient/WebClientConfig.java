package com.eri.configuration.rest.webclient;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ClientAuthorizationException;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizationFailureHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebClientConfig {

    @Bean
    public ReactiveClientRegistrationRepository reactiveClientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
        List<ClientRegistration> clientRegistrations = new ArrayList<>();
        oAuth2ClientProperties.getRegistration()
                .forEach((key, value) -> {
                    String tokenUri = oAuth2ClientProperties.getProvider().get(key).getTokenUri();
                    ClientRegistration clientRegistration = ClientRegistration
                            .withRegistrationId(key)
                            .tokenUri(tokenUri)
                            .clientId(value.getClientId())
                            .clientSecret(value.getClientSecret())
                            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                            .build();
                    clientRegistrations.add(clientRegistration);
                });

        return new InMemoryReactiveClientRegistrationRepository(clientRegistrations);
    }

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager
                = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2filterFunction
                = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth2filterFunction.setDefaultClientRegistrationId("movie-api");
        return WebClient.builder().filter(oauth2filterFunction).build();
    }

    private ReactiveOAuth2AuthorizationFailureHandler getReactiveOAuth2AuthorizationFailureHandler() {
        final ReactiveOAuth2AuthorizationFailureHandler reactiveOAuth2AuthorizationFailureHandler = (authorizationException, principal, attributes) -> {
            if (authorizationException instanceof ClientAuthorizationException) {
                ClientAuthorizationException clientAuthorizationException = (ClientAuthorizationException)authorizationException;
                return Mono.error(new Exception("401"));
            } else {
                return Mono.empty();
            }
        };
        return reactiveOAuth2AuthorizationFailureHandler;
    }
}
