package com.kodizim.findaduck.api.entry;


import com.kodizim.findaduck.api.model.Response;
import com.kodizim.findaduck.service.Entry.EntryService;
import com.kodizim.findaduck.domain.entry.AddEntryCommand;
import com.kodizim.findaduck.domain.entry.Advertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entry")
@RequiredArgsConstructor
@CrossOrigin
public class EntryController {


    private final EntryService entryService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addEntry(@RequestBody AddEntryCommand command, Principal principal) {
        entryService.addEntry(command, principal.getName());
    }

    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEntry(@PathVariable UUID entryId, Principal principal) {
        entryService.deleteEntry(entryId, principal.getName());
    }

    @PutMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateEntry(@PathVariable UUID entryId, @RequestBody AddEntryCommand command, Principal principal) {
        entryService.updateEntry(entryId, command, principal.getName());
    }

    @GetMapping("/search")
    Response<List<Advertisement>> entrySearch(@RequestParam String query, Principal principal) {
        return Response.of(entryService.search(query, principal.getName()));
    }

}
