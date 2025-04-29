package com.babsman.vertexai.config;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GoogleCloudCredentialsConfig {
    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        // Always load from resources
        try (var credentialsStream = getClass().getResourceAsStream("/service-account.json")) {
            if (credentialsStream == null) {
                throw new IOException("service-account.json not found in resources");
            }
            return GoogleCredentials.fromStream(credentialsStream)
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");
        }
    }
} 