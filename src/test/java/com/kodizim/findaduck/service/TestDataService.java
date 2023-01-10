package com.kodizim.findaduck.service;

import com.kodizim.findaduck.service.Entry.EntryService;
import com.kodizim.findaduck.service.user.CompanyService;
import com.kodizim.findaduck.service.user.EmployeeService;
import com.kodizim.findaduck.domain.company.Company;
import com.kodizim.findaduck.domain.company.CompanyInitialSetupCommand;
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
    private final CompanyService companyService;

    public Application addApplication(Entry entry) {
        entryService.apply(entry.getId(), "employee");
        return applicationRepository.findByEmployeeIdAndEntryId("employee", entry.getId()).orElseThrow();
    }


    public Company addCompany() {
        companyService.addCompanyUser("company", "company@company.com");
        companyService.companyInitialSetup(new CompanyInitialSetupCommand(
                "testCompany",
                "1231231231",
                "we are hiring for test",
                "/home",
                "someStreet",
                null,
                null,
                null

        ), "company");


        return companyRepository.findByCompanyId("company").orElseThrow();

    }

    public Entry addEntry() {
        String userId = "company";
        var user = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("Company not found!"));
        addMissingProfessions(List.of("IT", "COMPUTER", "JAVA"));
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
                List.of("IT", "COMPUTER", "JAVA")
        );
        entryRepository.save(entry);
        entryRepository.refreshEntrySearch();
        return entry;
    }
    public Entry addEntryDeveloper() {
        String userId = "company";
        var user = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("Company not found!"));
        addMissingProfessions(List.of("IT", "COMPUTER", "C#"));
        var entry = new Entry(
                UUID.randomUUID(),
                Category.IT,
                user.getCompanyId(),
                "Looking for a C# web developer",
                "C#per",
                new BigDecimal("12"),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                List.of("IT", "COMPUTER", "C#")
        );
        entryRepository.save(entry);
        entryRepository.refreshEntrySearch();
        return entry;
    }
    public Entry addEntryBarista() {
        String userId = "company";
        var user = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("Company not found!"));
        addMissingProfessions(List.of("COFFEE", "TEA", "BARISTA"));
        var entry = new Entry(
                UUID.randomUUID(),
                Category.IT,
                user.getCompanyId(),
                "We need a Barista guy",
                "A need for barista guy",
                new BigDecimal("12"),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                List.of("COFFEE", "TEA", "BARISTA")
        );
        entryRepository.save(entry);
        entryRepository.refreshEntrySearch();
        return entry;
    }
    public Entry addEntryAnotherBarista() {
        String userId = "company";
        var user = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("Company not found!"));
        addMissingProfessions(List.of("COFFEE", "TEA", "BARISTA"));
        var entry = new Entry(
                UUID.randomUUID(),
                Category.IT,
                user.getCompanyId(),
                "We need a guy to make us some coffee",
                "A need for coffee maker guy",
                new BigDecimal("12"),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                List.of("COFFEE", "TEA", "BARISTA")
        );
        entryRepository.save(entry);
        entryRepository.refreshEntrySearch();
        return entry;
    }


    private void addMissingProfessions(List<String> professions) {
        professions.stream()
                .filter(p -> !professionRepository.existsByProfessionName(p))
                .forEach(p -> professionRepository.save(
                        new Profession(UUID.randomUUID(), p)
                ));
    }

    public Employee addEmployee() {
        employeeService.employeeInitialSetup(new EmployeeInitialSetupCommand(
                "murat",
                "yıldırım",
                "123123",
                "/ev",
                LocalDate.of(1999, 3, 24),
                "selam ben murat",
                List.of("spring", "java", "sql")
        ), "employee");
        return employeeRepository.findByEmployeeId("employee").orElseThrow();

    }

    public Employee addEmployee(String userId) {
        employeeService.addEmployeeUser(userId, userId + "@mail.com");
        employeeService.employeeInitialSetup(new EmployeeInitialSetupCommand(
                "murat",
                "yıldırım",
                "123123",
                "/ev",
                LocalDate.of(1999, 3, 24),
                "selam ben murat",
                List.of("spring", "java", "sql")
        ), userId);
        return employeeRepository.findByEmployeeId(userId).orElseThrow();

    }

    public Company addCompany(String companyId) {
        companyService.addCompanyUser(companyId, companyId + "@mail.com");
        companyService.companyInitialSetup(new CompanyInitialSetupCommand(
                "company1",
                "1232114221",
                "we are hiring for test",
                "google/pics",
                "someStreet",
                "someCity",
                "someCountry",
                "78650"

        ), companyId);
        return companyRepository.findByCompanyId(companyId).orElseThrow();

    }

}
