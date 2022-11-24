package com.kodizim.kodforum.api.user;


import com.kodizim.kodforum.api.model.Response;
import com.kodizim.kodforum.application.user.EmployeeService;
import com.kodizim.kodforum.domain.employee.EmployeeDto;
import com.kodizim.kodforum.domain.employee.EmployeeInitialSetupCommand;
import com.kodizim.kodforum.domain.employee.EmployeeRepository;
import com.kodizim.kodforum.domain.employee.ProfessionRepository;
import com.kodizim.kodforum.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v2/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/initial-setup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void employeeInitialSetup(@RequestBody EmployeeInitialSetupCommand command, Principal principal){
        employeeService.employeeInitialSetup(command,principal.getName());
    }

    @GetMapping
    public Response<EmployeeDto> getEmployeeInfo(Principal principal){
        ;
        return Response.of(employeeService.getEmployeeDto(principal.getName()));
    }
}
