package com.likelion.plantication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "https://plantication.site", description = "도메인 설명")})
@SpringBootApplication
public class PlanticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanticationApplication.class, args);
    }

}
