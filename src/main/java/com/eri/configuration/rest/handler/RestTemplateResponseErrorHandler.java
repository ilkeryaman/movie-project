package com.eri.configuration.rest.handler;

import com.eri.exception.MovieNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (!HttpStatus.Series.SUCCESSFUL.equals(clientHttpResponse.getStatusCode().series()));
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        logger.error("RestTemplate ResponseErrorHandler called with HTTP Status Code: {}", clientHttpResponse.getStatusCode().value());

        HttpStatus httpStatus = clientHttpResponse.getStatusCode();

        if (httpStatus.is5xxServerError() && !HttpStatus.SERVICE_UNAVAILABLE.equals(clientHttpResponse.getStatusCode())) {
            throw new HttpServerErrorException(clientHttpResponse.getStatusCode());
        } else if (httpStatus.NOT_FOUND.equals(clientHttpResponse.getStatusCode())){
            throw new MovieNotFoundException();
        }
    }
}