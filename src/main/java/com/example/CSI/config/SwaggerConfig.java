package com.example.CSI.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // Déterminer l'URL du serveur en fonction du profil
        String serverUrl;
        if (List.of(environment.getActiveProfiles()).contains("prod")) {
            // En production, utilisez l'URL relative (Swagger détectera automatiquement l'URL de base)
            serverUrl = "/api";
        } else {
            // En développement, utilisez localhost
            serverUrl = "http://localhost:" + serverPort + "/api";
        }

        return new OpenAPI()
                .info(new Info()
                        .title("API Système de Réservation de salles et de matériel")
                        .description("""
                    Cette API permet de gérer les plannings des salles et matériels d'un établissement scolaire:
                    
                    - Gestion des enseignants et formations avec DTOs
                    - Réservation de salles et matériel
                    - Plannings et récapitulatifs
                    - Gestion des rôles (responsables/enseignants)
                    - Architecture moderne avec séparation des couches
                   
                    """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Équipe de Développement")
                                .email("piodjiele@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url(serverUrl)
                                .description(List.of(environment.getActiveProfiles()).contains("prod")
                                        ? "Serveur de production"
                                        : "Serveur de développement")
                ));
    }
}