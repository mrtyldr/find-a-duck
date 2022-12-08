package com.kodizim.kodforum.api.user;


import com.kodizim.kodforum.api.model.Response;
import com.kodizim.kodforum.application.Entry.EntryService;
import com.kodizim.kodforum.application.Entry.JobService;
import com.kodizim.kodforum.application.user.EmployeeService;
import com.kodizim.kodforum.domain.employee.EmployeeDto;
import com.kodizim.kodforum.domain.employee.EmployeeInitialSetupCommand;
import com.kodizim.kodforum.domain.job.JobDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EntryService entryService;
    private final JobService jobService;


    @PostMapping("/initial-setup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void employeeInitialSetup(@RequestBody EmployeeInitialSetupCommand command, Principal principal){
        employeeService.employeeInitialSetup(command,principal.getName());
    }

    @GetMapping("/")
    public Response<EmployeeDto> getEmployeeInfo(Principal principal){
        return Response.of(employeeService.getEmployeeDto(principal.getName()));
    }
    @PostMapping("/apply/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apply(@PathVariable UUID entryId, Principal principal){
        entryService.apply(entryId, principal.getName());
    }
    @GetMapping("/jobs")
    Response<List<JobDto>> jobs(Principal principal){
        var result = jobService.getEmployeeJobs(principal.getName());
        return Response.of(result);
    }

}
