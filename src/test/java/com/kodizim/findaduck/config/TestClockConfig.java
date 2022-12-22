package com.kodizim.findaduck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TestClockConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.parse("2021-04-01T16:30:00Z"), ZoneId.systemDefault());
    }
}
