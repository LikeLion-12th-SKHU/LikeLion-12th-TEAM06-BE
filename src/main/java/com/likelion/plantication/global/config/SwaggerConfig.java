package com.likelion.plantication.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v0.0.1")
                .title("Plantication Swagger API")
                .description("Plantication API");

        return new OpenAPI()
                .info(info);
    }
}
