package com.kodizim.findaduck.api.user;


import com.kodizim.findaduck.api.model.Response;
import com.kodizim.findaduck.service.Entry.EntryService;
import com.kodizim.findaduck.service.Entry.JobService;
import com.kodizim.findaduck.service.user.CompanyService;
import com.kodizim.findaduck.domain.company.CompanyDto;
import com.kodizim.findaduck.domain.company.CompanyInitialSetupCommand;
import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.company.UpdateCompanyCommand;
import com.kodizim.findaduck.domain.entry.Advertisement;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.ApplicationDto;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.domain.job.JobDto;
import com.kodizim.findaduck.domain.job.rateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@CrossOrigin
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    private final JobService jobService;
    private final ApplicationRepository applicationRepository;
    private final EntryService entryService;
    private final EntryRepository entryRepository;

    @PostMapping("/initial-setup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void companyInitialSetup(@RequestBody CompanyInitialSetupCommand command, Principal principal) {
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

    @GetMapping("/applications")
    Response<List<ApplicationDto>> getApplications(Principal principal){
        return Response.of(applicationRepository.getCompanyApplicationDto(principal.getName()));
    }
    @GetMapping("/applications/{entryId}")
    Response<List<ApplicationDto>> getApplications(@PathVariable UUID entryId,Principal principal){
        var professions = entryRepository.getProfessions(entryId)
                .toString();
        return Response.of(applicationRepository.getEntryApplications(entryId,principal.getName(),professions));
    }
    @GetMapping("/advertisements")
    Response<List<Advertisement>> getAdvertisements(Principal principal){
        return Response.of(entryService.getAdvertisementsForCompany(principal.getName()));
    }
    @GetMapping("")
    Response<CompanyDto> getCompany(Principal principal){
        return Response.of(companyService.getCompanyDto(principal.getName()));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCompany(@RequestBody UpdateCompanyCommand command, Principal principal){
        companyService.update(command,principal.getName());
    }

}
