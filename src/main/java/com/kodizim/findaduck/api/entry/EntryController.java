package com.kodizim.findaduck.api.entry;


import com.kodizim.findaduck.application.Entry.EntryService;
import com.kodizim.findaduck.domain.entry.AddEntryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/entry")
@RequiredArgsConstructor
@CrossOrigin
public class EntryController {


   private final EntryService entryService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addEntry(@RequestBody AddEntryCommand command, Principal principal){
       entryService.addEntry(command,principal.getName());
    }
    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEntry(@PathVariable UUID entryId, Principal principal){
        entryService.deleteEntry(entryId,principal.getName());
    }

}
