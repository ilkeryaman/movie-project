package com.eri.configuration.interceptor;

import com.eri.interceptor.AuthorizationCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@DependsOn("authorizationCheckInterceptor")
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AuthorizationCheckInterceptor authorizationCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationCheckInterceptor);
    }
}
