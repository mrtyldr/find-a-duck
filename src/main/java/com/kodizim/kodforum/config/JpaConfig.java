package com.kodizim.kodforum.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class JpaConfig {

        @Bean
        @ConditionalOnMissingBean(FlywayMigrationStrategy.class)
        public FlywayMigrationStrategy migrateStrategy() {
                return flyway -> {
                        flyway.repair();
                        flyway.migrate();
                };
        }
}
