package com.kodizim.kodforum.application.eventlistener;

import com.kodizim.kodforum.domain.entry.EntryRepository;
import com.kodizim.kodforum.domain.job.ApplicationAccepted;
import com.kodizim.kodforum.domain.job.Job;
import com.kodizim.kodforum.domain.job.JobRepository;
import com.kodizim.kodforum.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EntryListener {
    private final JobRepository jobRepository;
    private final EntryRepository entryRepository;
    private final Clock clock;

    @EventListener
    public void handleApplicationAccepted(ApplicationAccepted event) {
        var entry = entryRepository.findById(event.getEntryId()).orElseThrow(() -> new NotFoundException("entry not found"));
        var job = new Job(
                UUID.randomUUID(),
                event.getEntryId(),
                event.getEmployeeId(),
                entry.getJobStartDate()
        );
        jobRepository.save(job);
    }
}
