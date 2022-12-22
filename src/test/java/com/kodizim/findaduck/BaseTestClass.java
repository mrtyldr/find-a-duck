package com.kodizim.findaduck;

import com.kodizim.findaduck.application.TestDataService;
import com.kodizim.findaduck.application.user.CompanyService;
import com.kodizim.findaduck.application.user.EmployeeService;
import com.kodizim.findaduck.domain.company.Company;
import com.kodizim.findaduck.domain.employee.Employee;
import com.kodizim.findaduck.domain.entry.Entry;
import com.kodizim.findaduck.domain.job.Application;
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
import org.springframework.stereotype.Component;
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
    @Autowired
    TestDataService testDataService;
    @Autowired
    public Clock clock;

    protected  Company company;

    protected  Entry entry;

    protected Application application;

    protected Employee employee;



    @BeforeEach
    public void beforeEach(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate,tablesToClean.toArray(String[]::new));
        companyService.addCompanyUser("company","johndoe@mail.com");
        employeeService.addEmployeeUser("employee","janedoe@mail.com");
        if(company == null)
           company = testDataService.addCompany();
        if(employee == null)
            employee = testDataService.addEmployee();
        if(entry == null)
            entry = testDataService.addEntry();
        if(application == null && entry != null)
            application = testDataService.addApplication(entry);

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
