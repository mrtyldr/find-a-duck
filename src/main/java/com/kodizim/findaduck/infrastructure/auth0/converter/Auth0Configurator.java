package com.kodizim.findaduck.infrastructure.auth0.converter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface Auth0Configurator {
    void configure(HttpSecurity http) throws Exception;
}
