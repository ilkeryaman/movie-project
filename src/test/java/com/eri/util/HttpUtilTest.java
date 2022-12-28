package com.eri.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RunWith(MockitoJUnitRunner.class)
public class HttpUtilTest {

    @Test
    public void getDefaultHttpHeadersTest(){
        HttpUtil httpUtil = new HttpUtil();
        HttpHeaders httpHeaders = httpUtil.getDefaultHttpHeaders();
        Assert.assertTrue(httpHeaders.getAccept().contains(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getHttpEntity(){
        HttpUtil httpUtil = new HttpUtil();
        HttpEntity httpEntity = httpUtil.getHttpEntity();
        Assert.assertNotNull(httpEntity.getHeaders());
        Assert.assertTrue(httpEntity.getHeaders().getAccept().contains(MediaType.APPLICATION_JSON));
    }
}
