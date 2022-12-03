package com.kodizim.kodforum.application.user;

import com.kodizim.kodforum.BaseTestClass;
import com.kodizim.kodforum.domain.employee.EmployeeInitialSetupCommand;
import com.kodizim.kodforum.domain.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest extends BaseTestClass {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;
    @Test
    void getEmployeeDto() {
       addEmployee();
       var employeeDto = employeeService.getEmployeeDto("employee");
       assertThat(employeeDto).isNotNull();
       assertThat(employeeDto.getProfessions()).containsExactlyInAnyOrder("spring","java","sql");
       cleanBeforeAndAfter("employee");
    }

    private void addEmployee(){
        employeeService.employeeInitialSetup(new EmployeeInitialSetupCommand(
                "murat",
                "yıldırım",
                "123123",
                "/ev",
                LocalDate.of(1999,3,24),
                "selam ben murat",
                List.of("spring","java","sql")
        ),"employee");
    }
}