package com.josereis.usermanagerapi.configurarion;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${openapi.application-version}")
    public String applicationVersion;

    @Value("${openapi.application-title}")
    public String applicationTitle;

    @Value("${openapi.application-description}")
    public String applicationDescription;

    @Value("${openapi.application-termsOfService}")
    public String applicationTermsOfService;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(
                        new Info()
                                .title(applicationTitle)
                                .description(applicationDescription)
                                .version(applicationVersion)
                                .license(new License().name("License of API").url(applicationTermsOfService))
                );
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}