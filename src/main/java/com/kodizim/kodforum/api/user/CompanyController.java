package com.kodizim.kodforum.api.user;


import com.kodizim.kodforum.domain.company.Company;
import com.kodizim.kodforum.domain.company.CompanyInitialSetupCommand;
import com.kodizim.kodforum.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyRepository companyRepository;

    @PostMapping("/initial-setup")
    public void companyInitialSetup(@RequestBody CompanyInitialSetupCommand command, Principal principal){
        var company = new Company(
                UUID.randomUUID(),
                principal.getName(),
                command.getCompanyName(),
                command.getPhoneNumber(),
                command.getAbout(),
                command.getPhotoLocationKey()
        );
        companyRepository.save(company);
    }
}
