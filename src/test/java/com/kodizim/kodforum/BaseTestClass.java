package com.kodizim.kodforum;

import com.kodizim.kodforum.application.user.EmployeeService;
import com.kodizim.kodforum.domain.employee.EmployeeInitialSetupCommand;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseTestClass{
    private final LinkedHashSet<String> tablesToClean = new LinkedHashSet<String>();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmployeeService employeeService;
    @BeforeEach
    public void beforeEach(){

        JdbcTestUtils.deleteFromTables(jdbcTemplate,tablesToClean.toArray(String[]::new));


    }


    protected void cleanBeforeAndAfter(String... tableNames) {
        Collections.addAll(tablesToClean, tableNames);
        // We shouldn't delete "user" record. So we never clear users table.
        tablesToClean.remove("users");
    }


    @Configuration
    @ComponentScan(basePackageClasses = BaseTestClass.class)
    static class TestConfig {

        @Bean
        FlywayMigrationStrategy clean() {
            return flyway -> {
                flyway.clean();
                flyway.migrate();
            };
        }
    }
}
