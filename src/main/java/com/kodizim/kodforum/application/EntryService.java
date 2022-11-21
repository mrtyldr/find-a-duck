package com.kodizim.kodforum.application;

import com.kodizim.kodforum.api.EntryController;
import com.kodizim.kodforum.domain.entry.Entry;
import com.kodizim.kodforum.domain.entry.EntryRepository;
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
        return null;
    }

    public List<Entry> getEntries() {
     return entryRepository.findAll();
    }
}
