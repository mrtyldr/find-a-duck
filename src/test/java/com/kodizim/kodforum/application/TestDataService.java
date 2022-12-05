package com.kodizim.kodforum.application;

import com.kodizim.kodforum.application.Entry.EntryService;
import com.kodizim.kodforum.domain.company.Company;
import com.kodizim.kodforum.domain.company.CompanyRepository;
import com.kodizim.kodforum.domain.entry.AddEntryCommand;
import com.kodizim.kodforum.domain.entry.Category;
import com.kodizim.kodforum.domain.entry.EntryRepository;
import com.kodizim.kodforum.domain.job.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TestDataService {

    private final EntryService entryService;

    private final EntryRepository entryRepository;

    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    public UUID addEntryAndApplication(){
        var entryCommand = new AddEntryCommand(
                Category.IT,
                "IT uzmanı arıyoz",
                "IT uzmanı",
                new BigDecimal("25"),
                OffsetDateTime.now(),
                List.of("spring")

        );
        entryService.addEntry(entryCommand,"company");
        var entry = entryRepository.findByCompanyId("company").get(0);
        entryService.apply(entry.getId(),"employee");
        return applicationRepository.findByEmployeeId("employee").orElseThrow().getApplicationId();
    }

    public String addCompany() {
        var company = new Company(
                "company",
                "testCompany",
                "1231231231",
                "company@company.com",
                "we are hiring for test",
                "/home",
                null,null,null,null
        );
        companyRepository.save(company);
        return company.getCompanyId();

    }


}
