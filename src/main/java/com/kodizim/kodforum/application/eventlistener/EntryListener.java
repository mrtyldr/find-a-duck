package com.kodizim.kodforum.application.eventlistener;

import com.kodizim.kodforum.domain.job.ApplicationAccepted;
import com.kodizim.kodforum.domain.job.Job;
import com.kodizim.kodforum.domain.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EntryListener {
    private final JobRepository jobRepository;
    @EventListener
    public void handleApplicationAccepted(ApplicationAccepted event){
        var job = new Job(UUID.randomUUID()
        ,event.getEntryId(),event.getEmployeeId());
        jobRepository.save(job);
    }
}
