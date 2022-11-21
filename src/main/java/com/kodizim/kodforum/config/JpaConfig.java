package com.kodizim.kodforum.config;


import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.vladmihalcea.spring.repository",
                "com.kodizim.kodforum.domain"
        }
)
@RequiredArgsConstructor
public class JpaConfig  extends PostgreSQL10Dialect {

}
