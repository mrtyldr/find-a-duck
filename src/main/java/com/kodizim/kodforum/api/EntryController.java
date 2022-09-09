package com.kodizim.kodforum.api;


import com.kodizim.kodforum.entity.Entry;
import com.kodizim.kodforum.service.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

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
    public UUID addEntry(@RequestBody AddEntryCommand command){
        return entryService.addEntry(command);
    }

    @GetMapping("/")
    public Entry EntrygetEntries() {
       var entries = entryService.getEntries().get(0);
       return entries;
    }
}
