package com.PUMB.test.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Test task for PUMB",
                version = "1.0.0",
                description = "This app gives functionality to upload CSV or XML files, " +
                        "read and validate info from it, and " +
                        "store information from it in PostgreSQL DB",
                contact = @Contact(
                        email = "dimanakonechnui7@gmail.com"
                )
        ),
        servers = @Server(
                description = "Dev Environment",
                url = "http://localhost:8080"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springdoc-public")
                .pathsToMatch("/**")
                .build();
    }
}