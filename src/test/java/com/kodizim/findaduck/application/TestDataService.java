package com.kodizim.findaduck.application;

import com.kodizim.findaduck.application.Entry.EntryService;
import com.kodizim.findaduck.application.user.EmployeeService;
import com.kodizim.findaduck.domain.company.Company;
import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.employee.*;
import com.kodizim.findaduck.domain.entry.Category;
import com.kodizim.findaduck.domain.entry.Entry;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.Application;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class TestDataService {

    private final EntryService entryService;

    private final EntryRepository entryRepository;

    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    private final ProfessionRepository professionRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public Application addApplication(Entry entry){
        entryService.apply(entry.getId(),"employee");
        return applicationRepository.findByEmployeeIdAndEntryId("employee",entry.getId()).orElseThrow();
    }


    public Entry addEntry() {
        String userId = "company";
        var user = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("Company not found!"));
       addMissingProfessions(List.of("IT","COMPUTER","JAVA"));
        var entry = new Entry(
                UUID.randomUUID(),
                Category.IT,
                user.getCompanyId(),
                "Looking for an IT guy",
                "IT guy",
                new BigDecimal("12"),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                List.of("IT","COMPUTER","JAVA")
        );
        entryRepository.save(entry);
        return entry;
    }

    public Company addCompany() {
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
        return company;

    }
    private void addMissingProfessions(List<String> professions) {
        professions.stream()
                .filter(p -> !professionRepository.existsByProfessionName(p))
                .forEach(p -> professionRepository.save(
                        new Profession(UUID.randomUUID(), p)
                ));
    }
   public Employee addEmployee(){


        employeeService.employeeInitialSetup(new EmployeeInitialSetupCommand(
                "murat",
                "yıldırım",
                "123123",
                "/ev",
                LocalDate.of(1999,3,24),
                "selam ben murat",
                List.of("spring","java","sql")
        ),"employee");
       return employeeRepository.findByEmployeeId("employee").orElseThrow();

    }




}
