package com.eri.configuration.rest.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info =
    @Info(title = "Movie API", version = "1.0", description = "This API is developed for learning purposes.")
)
@Configuration
public class OpenApiConfig { }
