package com.kodizim.findaduck;

import com.kodizim.findaduck.application.user.CompanyService;
import com.kodizim.findaduck.application.user.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.LinkedHashSet;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseTestClass{
    private final LinkedHashSet<String> tablesToClean = new LinkedHashSet<String>();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CompanyService companyService;


    @BeforeEach
    public void beforeEach(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate,tablesToClean.toArray(String[]::new));
        companyService.addCompanyUser("company","johndoe@mail.com");
        employeeService.addEmployeeUser("employee","janedoe@mail.com");
    }




    protected void cleanBeforeAndAfter(String... tableNames) {
        Collections.addAll(tablesToClean, tableNames);
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
