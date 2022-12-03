package com.kodizim.kodforum.application.user;

import com.kodizim.kodforum.domain.company.Company;
import com.kodizim.kodforum.domain.company.CompanyInitialSetupCommand;
import com.kodizim.kodforum.domain.company.CompanyRepository;
import com.kodizim.kodforum.domain.entry.EntryRepository;
import com.kodizim.kodforum.domain.job.ApplicationRepository;
import com.kodizim.kodforum.error.NotFoundException;
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

    public void acceptApplication(UUID applicationId, String userId) {
        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("application not found"));
        //entryRepository.isValidEntry(userId, application.getEntryId());
        application.accept();

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
                command.getCompanyName(),
                command.getPhoneNumber(),
                command.getAbout(),
                command.getPhotoLocationKey(),
                command.getAddressLine(),
                command.getCity(),
                command.getCountry(),
                command.getPostalCode()

        );
        companyRepository.save(company);

    }
}
