package org.cb.minilms.config.security.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini LMS")
                        .version("1.0")
                        .description("API documentation with JWT auth"))
                .servers(List.of(
                                new Server().url("http://localhost:8080").description("Local"),
                                new Server().url("https://dev.miapi.com").description("Development"),
                                new Server().url("https://staging.miapi.com").description("Staging"),
                                new Server().url("https://api.miapi.com").description("Production")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // Grupo para todos los endpoints de /api/books/**
    @Bean
    public GroupedOpenApi booksApi() {
        return GroupedOpenApi.builder()
                .group("Books Api") // nombre que verás en el menú
                .pathsToMatch("/api/books/**")
                .build();
    }

    // Grupo para todos los endpoints de /api/book/**
    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("Users Api") // nombre que verás en el menú
                .pathsToMatch("/api/users/**")
                .build();
    }

}