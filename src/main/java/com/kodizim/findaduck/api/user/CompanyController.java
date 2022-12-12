package com.kodizim.findaduck.api.user;


import com.kodizim.findaduck.api.model.Response;
import com.kodizim.findaduck.application.Entry.JobService;
import com.kodizim.findaduck.application.user.CompanyService;
import com.kodizim.findaduck.domain.company.CompanyInitialSetupCommand;
import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.job.JobDto;
import com.kodizim.findaduck.domain.job.rateCommand;
import com.kodizim.findaduck.error.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    private final JobService jobService;

    @PostMapping("/initial-setup")
    public void companyInitialSetup(@RequestBody CompanyInitialSetupCommand command, Principal principal) {
        if (companyRepository.existsByCompanyId(principal.getName()))
            throw new AlreadyExistsException("company already exists");
        companyService.companyInitialSetup(command,principal.getName());
    }

    @PostMapping("/accept-application/{applicationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptApplication(@PathVariable UUID applicationId, Principal principal) {
        companyService.acceptApplication(applicationId, principal.getName());
    }

    @PostMapping("/reject-application/{applicationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectApplication(@PathVariable UUID applicationId,Principal principal){
        companyService.rejectApplication(applicationId,principal.getName());
    }

    @GetMapping("/jobs")
    Response<List<JobDto>> jobs(Principal principal){
        var result = jobService.getCompanyJobs(principal.getName());
        return Response.of(result);
    }

    @PostMapping("/rate-employee/{jobId}")
    public void rateEmployee(@PathVariable UUID jobId, @RequestBody rateCommand command, Principal principal){
        jobService.rateEmployee(jobId,command.rate(),principal.getName());
    }

}
