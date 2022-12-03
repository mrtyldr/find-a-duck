package com.kodizim.kodforum.application;

import com.kodizim.kodforum.domain.company.CompanyRepository;
import com.kodizim.kodforum.domain.employee.EmployeeRepository;
import com.kodizim.kodforum.domain.employee.Profession;
import com.kodizim.kodforum.domain.employee.ProfessionRepository;
import com.kodizim.kodforum.domain.entry.AddEntryCommand;
import com.kodizim.kodforum.domain.entry.Entry;
import com.kodizim.kodforum.domain.entry.EntryRepository;
import com.kodizim.kodforum.domain.job.Application;
import com.kodizim.kodforum.domain.job.ApplicationRepository;
import com.kodizim.kodforum.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final CompanyRepository companyRepository;
    private final ProfessionRepository professionRepository;

    private final EmployeeRepository employeeRepository;
    private final ApplicationRepository applicationRepository;

    private final Clock clock;

    @Transactional
    public void addEntry(AddEntryCommand command, String userId) {
        var user = companyRepository.findByCompanyId(userId)
                .orElseThrow(() -> new NotFoundException("Company not found!"));
        addMissingProfessions(command.getExpectedProfessions());
        var entry = new Entry(
                UUID.randomUUID(),
                command.getCategory(),
                user.getCompanyId(),
                command.getTitle(),
                command.getContent(),
                command.getHourlyPay(),
                command.getValidTil(),
                OffsetDateTime.now(clock),
                OffsetDateTime.now(clock),
                professionRepository.findProfessionsByName(command.getExpectedProfessions())
        );
        entryRepository.save(entry);
    }

    public List<Entry> getEntries(Pageable pageable, String userId) {

        return entryRepository.findAll(pageable).getContent();
    }

    private List<UUID> getProfessionIds(List<String> professionNames){
        return professionRepository.findProfessionsByName(professionNames);
    }

    private void addMissingProfessions(List<String> professions) {
        professions.stream()
                .filter(p -> !professionRepository.existsByProfessionName(p))
                .forEach(p -> professionRepository.save(
                        new Profession(UUID.randomUUID(), p)
                ));
    }

    @Transactional
    public void apply(UUID entryId, String userId){
        var employee = employeeRepository.findByEmployeeId(userId)
                .orElseThrow(() -> new NotFoundException("employee not found"));
        var application = new Application(entryId,employee.getEmployeeId(),OffsetDateTime.now(clock));
        applicationRepository.save(application);
    }
}
