package com.kodizim.kodforum.api;


import com.kodizim.kodforum.api.model.Response;
import com.kodizim.kodforum.domain.entry.AddEntryCommand;
import com.kodizim.kodforum.domain.entry.Entry;
import com.kodizim.kodforum.application.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/entry")
@RequiredArgsConstructor
public class EntryController {


   private final EntryService entryService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addEntry(@RequestBody AddEntryCommand command, Principal principal){
       entryService.addEntry(command,principal.getName());
    }

    @GetMapping("/")
    public Response<List<Entry>> getEntries(Pageable pageable, Principal principal) {
        return Response.of(entryService.getEntries(pageable,principal.getName()));
    }
}
