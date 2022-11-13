package com.kodizim.kodforum.service;

import com.kodizim.kodforum.api.EntryController;
import com.kodizim.kodforum.entity.Entry;
import com.kodizim.kodforum.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;

    public UUID addEntry(EntryController.AddEntryCommand command, String userId){
        var id = UUID.randomUUID();
        var entry = new Entry(id,command.getCategoryId(), command.getTitle(),command.getContent(),new BigDecimal("25.0"),OffsetDateTime.now(), OffsetDateTime.now(),OffsetDateTime.now());
        entryRepository.save(entry);
        return id;
    }

    public List<Entry> getEntries() {
        var entries = entryRepository.findAll();
        return entries;
    }
}
