package com.kodizim.kodforum.api.user;


import com.kodizim.kodforum.application.user.CompanyService;
import com.kodizim.kodforum.domain.company.CompanyInitialSetupCommand;
import com.kodizim.kodforum.domain.company.CompanyRepository;
import com.kodizim.kodforum.error.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

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

}
