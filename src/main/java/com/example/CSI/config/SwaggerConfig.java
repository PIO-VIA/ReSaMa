package com.example.CSI.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Système de Reservation de salles et de ")
                        .description("""
                    Cette API permet de gérer les  planning des salles et materiels d'une  application:
                   
                    """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Équipe de Développement")
                                .email("piodjiele@gmail.com")
                                .url("https://schoolmanagement.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + "/api")
                                .description("Serveur de développement local"),
                        new Server()
                                .url("https://api.schoolmanagement.com")
                                .description("Serveur de production")
                ));

    }
}
