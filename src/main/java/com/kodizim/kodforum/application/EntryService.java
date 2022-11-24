package com.kodizim.kodforum.application;

import com.kodizim.kodforum.domain.company.CompanyRepository;
import com.kodizim.kodforum.domain.entry.AddEntryCommand;
import com.kodizim.kodforum.domain.entry.Entry;
import com.kodizim.kodforum.domain.entry.EntryRepository;
import com.kodizim.kodforum.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void addEntry(AddEntryCommand command, String userId){
        var user = companyRepository.findByUserId(userId).orElseThrow(()-> new NotFoundException("user not found!"));
        var entry = new Entry(
                UUID.randomUUID(),
                command.getCategoryId(),
                user.getCompanyId(),
                command.getTitle(),
                command.getContent(),
                command.getHourlyPay(),
                command.getValidTil(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                command.getExpectedProfessions()
        );
        entryRepository.save(entry);
    }

    public List<Entry> getEntries() {
     return entryRepository.findAll();
    }
}
