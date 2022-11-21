package com.kodizim.kodforum.infrastructure.auth0;

import com.auth0.client.auth.AuthAPI;
import com.kodizim.kodforum.infrastructure.auth0.converter.Auth0Configurator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;


@Configuration
@RequiredArgsConstructor
public class Auth0AuthConfigurator implements Auth0Configurator {
    private final Auth0Properties properties;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(config -> config.jwt().decoder(kodForumJwtDecoder())
                .jwtAuthenticationConverter(new Auth0JwtPermissionsConverter("STANDARD")));
    }

    @Bean
    JwtDecoder kodForumJwtDecoder() {
        return jwtDecoder(properties.getIssuerUri(), properties.getAudience());
    }

    JwtDecoder jwtDecoder(String issuer, String audience) {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }

    @RequiredArgsConstructor
    static class AudienceValidator implements OAuth2TokenValidator<Jwt> {

        final String audience;

        @Override
        public OAuth2TokenValidatorResult validate(Jwt jwt) {
            OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);

            if (jwt.getAudience().contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(error);
        }
    }

    @Bean
    AuthAPI AuthAPI() {
        return new AuthAPI(
                properties.getDomain(),
                properties.getClientId(),
                properties.getClientSecret());
    }
}
