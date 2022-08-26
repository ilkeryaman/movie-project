package com.eri.configuration.rest.resttemplate.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MovieOAuth2ClientContext extends DefaultOAuth2ClientContext {

    @Value("${spring.security.oauth2.client.movie-api.expireCorrectionInSeconds}")
    private Long expireCorrectionInSeconds;

    @Override
    public void setAccessToken(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken movieApiAccessToken = new DefaultOAuth2AccessToken(accessToken);
        movieApiAccessToken.setExpiration(arrangeExpirationDate(accessToken.getExpiration()));
        super.setAccessToken(movieApiAccessToken);
    }

    private Date arrangeExpirationDate(Date expiration) {
        return Date.from(expiration.toInstant().minusSeconds(getExpireCorrectionInSeconds()));
    }

    public Long getExpireCorrectionInSeconds() {
        return expireCorrectionInSeconds;
    }
}
