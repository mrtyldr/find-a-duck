package com.kodizim.kodforum.security;

import com.kodizim.kodforum.security.converter.Auth0Configurator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Auth0Configurator auth0Configurator;
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        // -- Swagger UI v2
                        "/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        // -- Swagger UI v3 (OpenAPI)
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/countries",
                        "/dummy/**",
                        "/i18n/**"
                ).permitAll()
                .antMatchers("/salt-edge/callbacks/**").permitAll()
                .anyRequest().hasAuthority("STANDARD");

        auth0Configurator.configure(http);
    }
}
