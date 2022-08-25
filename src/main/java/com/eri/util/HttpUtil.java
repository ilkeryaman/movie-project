package com.eri.util;

import org.springframework.http.*;

import java.util.Arrays;

public class HttpUtil {

    public static HttpHeaders getDefaultHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static HttpEntity getHttpEntity(){
        return new HttpEntity<>(getDefaultHttpHeaders());
    }
}
