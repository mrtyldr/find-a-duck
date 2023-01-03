package com.kodizim.findaduck.application.Entry;

import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.employee.Profession;
import com.kodizim.findaduck.domain.employee.ProfessionRepository;
import com.kodizim.findaduck.domain.entry.*;
import com.kodizim.findaduck.domain.job.Application;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.error.AlreadyExistsException;
import com.kodizim.findaduck.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        addMissingProfessions(command.expectedProfessions());
        var entry = new Entry(
                UUID.randomUUID(),
                command.category(),
                user.getCompanyId(),
                command.title(),
                command.content(),
                command.hourlyPay(),
                command.jobStartDate(),
                command.validTil(),
                OffsetDateTime.now(clock),
                OffsetDateTime.now(clock),
                command.expectedProfessions()
        );
        entryRepository.save(entry);
        entryRepository.refreshActiveEntries();
        entryRepository.refreshEntrySearch();
    }


    private List<UUID> getProfessionIds(List<String> professionNames) {
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
    public void apply(UUID entryId, String userId) {
        var employee = employeeRepository.findByEmployeeId(userId)
                .orElseThrow(() -> new NotFoundException("employee not found"));
        if (applicationRepository.existsByEntryIdAndEmployeeId(entryId, userId))
            throw new AlreadyExistsException("You already Have Applied to this ad");
        var application = new Application(entryId, employee.getEmployeeId(), OffsetDateTime.now(clock));
        applicationRepository.save(application);
    }

    public List<Advertisement> getAdvertisements(String employeeId) {
        String professions = employeeRepository.getProfessions(employeeId)
                .toString();
        var entryDtos = entryRepository.getEntryDto(employeeId, professions);
        return entryDtos.stream().map(e -> toAdvertisement(e, employeeId))
                .collect(Collectors.toList());
    }

    private Advertisement toAdvertisement(EntryDto entryDto, String employeeId) {

        return new Advertisement(
                entryDto.getEntryId(),
                entryDto.getCompanyName(),
                entryDto.getCategory(),
                entryDto.getHourlyPay(),
                entryDto.getTitle(),
                entryDto.getContent(),
                entryDto.getJobStartDate(),
                entryDto.getValidTil(),
                entryDto.getCreatedOn(),
                applicationRepository.existsByEntryIdAndEmployeeId(entryDto.getEntryId(), employeeId),
                entryDto.getExpectedProfessions()
        );
    }

    public List<Advertisement> getAdvertisementsForCompany(String companyId) {
        var entryDtos = entryRepository.getEntryDtoForCompany(companyId);
        return entryDtos.stream().map(e -> toAdvertisement(e, companyId))
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0,6,12,18 ? * * *")
    private void updateEntries() {
        var activeEntries = entryRepository.getActiveEntries();
        activeEntries.forEach(this::markClosedEntries);
        entryRepository.refreshActiveEntries();
    }

    @Transactional
    public void markClosedEntries(Entry entry) {
        if (entry.getValidTil().isAfter(OffsetDateTime.now(clock)))
            entry.entryClosed();
        entryRepository.save(entry);
    }

    @Transactional
    public void deleteEntry(UUID entryId, String companyId) {
        if (!entryRepository.existsByCompanyIdAndId(companyId, entryId))
            throw new NotFoundException("Ad not found !");
        var entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new NotFoundException("Entry Not Found!"));
        entryRepository.delete(entry);
        entryRepository.refreshActiveEntries();
        entryRepository.refreshEntrySearch();
    }

    @Transactional
    public void updateEntry(UUID entryId, AddEntryCommand command, String companyId) {
        var entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new NotFoundException("Entry Not Found!"));
        if (!entry.getCompanyId().equals(companyId))
            throw new NotFoundException("Entry Not Found");
        entry.update(command);

        entryRepository.save(entry);
        entryRepository.refreshActiveEntries();
        entryRepository.refreshEntrySearch();
    }

    public List<Advertisement> search(String searchString, String userId) {
        if (companyRepository.existsByCompanyId(userId)) {
            var searchQuery = searchString.replace(" ", "|");
            return entryRepository.entrySearchCompany(searchQuery, userId)
                    .stream().map(e -> toAdvertisement(e, userId))
                    .collect(Collectors.toList());
        } else if (employeeRepository.existsByEmployeeId(userId)) {
            var searchQuery = searchString.replace(" ", "|");
            return entryRepository.entrySearchEmployee(searchQuery)
                    .stream().map(e -> toAdvertisement(e, userId)).collect(Collectors.toList());
        } else {
            throw new NotFoundException("user not found");
        }
    }
}
