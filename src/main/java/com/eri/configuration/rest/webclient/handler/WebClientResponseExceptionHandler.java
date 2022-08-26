package com.eri.configuration.rest.webclient.handler;

import com.eri.constant.enums.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class WebClientResponseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebClientResponseExceptionHandler.class);

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponseExceptions(WebClientResponseException ex) {
        logger.error("RestTemplate ResponseErrorHandler called with HTTP Status Code: {}", ex.getStatusCode().value());
        HttpStatus httpStatus = ex.getStatusCode();
        if (httpStatus.NOT_FOUND.equals(httpStatus)) {
            return new ResponseEntity<>(ErrorMessage.MOVIE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ErrorMessage.INTERNAL_SERVER_ERROR.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
