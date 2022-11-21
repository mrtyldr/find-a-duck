package com.kodizim.kodforum.infrastructure.auth0;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("auth0")
public class Auth0Properties {
    private String domain;
    private String clientId;
    private String clientSecret;
    private String audience;
    private String managementAudience;
    private String issuerUri;
    private  String userDatabase;
}
