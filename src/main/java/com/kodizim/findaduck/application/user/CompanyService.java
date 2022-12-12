package com.kodizim.findaduck.application.user;

import com.kodizim.findaduck.domain.company.Company;
import com.kodizim.findaduck.domain.company.CompanyInitialSetupCommand;
import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;
    private final EntryRepository entryRepository;

    @Transactional
    public void acceptApplication(UUID applicationId, String userId) {
        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("application not found"));
        if (!entryRepository.existsByCompanyIdAndId(userId, application.getEntryId()))
            throw new NotFoundException("entry not found!");
        application.accept();
        applicationRepository.save(application);
    }

    public void addCompanyUser(String userId, String email) {
        var company = new Company(userId, email);
        companyRepository.save(company);
    }

    @Transactional
    public void companyInitialSetup(CompanyInitialSetupCommand command, String userId) {
        var company = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("company not found!"));
        company.companyInitial(
                command.companyName(),
                command.phoneNumber(),
                command.about(),
                command.photoLocationKey(),
                command.addressLine(),
                command.city(),
                command.country(),
                command.postalCode()

        );
        companyRepository.save(company);

    }

    @Transactional
    public void rejectApplication(UUID applicationId, String userId) {
        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("application not found"));

        if (!entryRepository.existsByCompanyIdAndId(userId, application.getEntryId()))
            throw new NotFoundException("entry not found!");

        application.reject();

        applicationRepository.save(application);
    }
}
