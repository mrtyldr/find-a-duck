package com.kodizim.findaduck.api.user;


import com.kodizim.findaduck.api.model.Response;
import com.kodizim.findaduck.application.Entry.EntryService;
import com.kodizim.findaduck.application.Entry.JobService;
import com.kodizim.findaduck.application.user.EmployeeService;
import com.kodizim.findaduck.domain.employee.EmployeeDto;
import com.kodizim.findaduck.domain.employee.EmployeeInitialSetupCommand;
import com.kodizim.findaduck.domain.employee.UpdateEmployeeCommand;
import com.kodizim.findaduck.domain.entry.Advertisement;
import com.kodizim.findaduck.domain.job.ApplicationDto;
import com.kodizim.findaduck.domain.job.JobDto;
import com.kodizim.findaduck.domain.job.rateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EntryService entryService;
    private final JobService jobService;


    @PostMapping("/initial-setup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void employeeInitialSetup(@RequestBody EmployeeInitialSetupCommand command, Principal principal) {
        employeeService.employeeInitialSetup(command, principal.getName());
    }

    @GetMapping("")
    public Response<EmployeeDto> getEmployeeInfo(Principal principal) {
        return Response.of(employeeService.getEmployeeDto(principal.getName()));
    }

    @PostMapping("/apply/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apply(@PathVariable UUID entryId, Principal principal) {
        entryService.apply(entryId, principal.getName());
    }

    @GetMapping("/jobs")
    Response<List<JobDto>> jobs(Principal principal) {
        var result = jobService.getEmployeeJobs(principal.getName());
        return Response.of(result);
    }

    @PostMapping("/rate-company/{jobId}")
    public void rateCompany(@PathVariable UUID jobId, @RequestBody rateCommand command, Principal principal) {
        jobService.rateCompany(jobId, command.rate(), principal.getName());
    }

    @GetMapping("/applications")
    public Response<List<ApplicationDto>> getApplications(Principal principal) {
        var result = employeeService.getApplications(principal.getName());
        return Response.of(result);
    }
    @GetMapping("/advertisements")
    Response<List<Advertisement>> getAdvertisements(Principal principal){
        return Response.of(entryService.getAdvertisements(principal.getName()));
    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateEmployee(@RequestBody UpdateEmployeeCommand command, Principal principal){
        employeeService.update(command,principal.getName());
    }

}
