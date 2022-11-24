package com.kodizim.kodforum.api;


import com.kodizim.kodforum.domain.entry.AddEntryCommand;
import com.kodizim.kodforum.domain.entry.Entry;
import com.kodizim.kodforum.application.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/entry")
@RequiredArgsConstructor
public class EntryController {


   private final EntryService entryService;

    @PostMapping("/")
    public void addEntry(@RequestBody AddEntryCommand command, Principal principal){
       entryService.addEntry(command,principal.getName());
    }

    @GetMapping("/")
    public List<Entry> EntrygetEntries() {
       var entries = entryService.getEntries();
       return entries;
    }
}
