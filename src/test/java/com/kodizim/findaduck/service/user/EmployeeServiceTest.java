package com.kodizim.findaduck.service.user;

import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.employee.ProfessionRepository;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class EmployeeServiceTest  {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    ProfessionRepository professionRepository;
    @Mock
    ApplicationRepository applicationRepository;




}