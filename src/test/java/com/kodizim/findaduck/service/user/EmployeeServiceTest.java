package com.kodizim.findaduck.service.user;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeServiceTest extends BaseTestClass {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    JobRepository jobRepository;



}