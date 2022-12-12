package com.kodizim.findaduck.application.user;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.application.Entry.JobService;
import com.kodizim.findaduck.application.TestDataService;
import com.kodizim.findaduck.domain.employee.EmployeeInitialSetupCommand;
import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.job.Job;
import com.kodizim.findaduck.domain.job.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeServiceTest extends BaseTestClass {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    JobRepository jobRepository;

    @Test
    void getEmployeeDto() {
       addEmployee();
       var employeeDto = employeeService.getEmployeeDto("employee");
       assertThat(employeeDto).isNotNull();
       assertThat(employeeDto.getProfessions()).containsExactlyInAnyOrder("spring","java","sql");
       cleanBeforeAndAfter("employee");
    }

    private void addEmployee(){

        //TODO: make this test more reliable and comprehensive
        employeeService.employeeInitialSetup(new EmployeeInitialSetupCommand(
                "murat",
                "yıldırım",
                "123123",
                "/ev",
                LocalDate.of(1999,3,24),
                "selam ben murat",
                List.of("spring","java","sql")
        ),"employee");
        Job job = new Job(UUID.randomUUID(),UUID.randomUUID(),"employee", OffsetDateTime.now());
        job.rateEmployee(3);
        jobRepository.save(job);
    }
}