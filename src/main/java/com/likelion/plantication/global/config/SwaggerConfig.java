package com.likelion.plantication.global.config;


import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://plantication.site"))
                .info(new Info().title("Plantication API")
                        .description("API documentation for Plantication")
                        .version("v1.0.0"));
    }
}