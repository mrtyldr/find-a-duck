package com.kodizim.kodforum.api;


import com.kodizim.kodforum.domain.entry.Entry;
import com.kodizim.kodforum.application.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/entry")
@RequiredArgsConstructor
public class EntryController {


   private final EntryService entryService;
   @Value
   public static class AddEntryCommand {
       UUID categoryId;
       String title;
       String content;
   }

    @PostMapping("/")
    public UUID addEntry(@RequestBody AddEntryCommand command, Principal principal){

       return entryService.addEntry(command,principal.getName());
    }

    @GetMapping("/")
    public List<Entry> EntrygetEntries() {
       var entries = entryService.getEntries();
       return entries;
    }
}
