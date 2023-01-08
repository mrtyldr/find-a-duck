package com.kodizim.findaduck.service.Entry;

import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.Job;
import com.kodizim.findaduck.domain.job.JobRepository;
import com.kodizim.findaduck.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

class JobServiceTest {

    JobService jobService;

    JobRepository jobRepository;

    EntryRepository entryRepository;

    @BeforeEach
    void set_up() {
       jobRepository = Mockito.mock(JobRepository.class);
       entryRepository = Mockito.mock(EntryRepository.class);
       jobService = new JobService(jobRepository,entryRepository);
    }


    @Test
    void should_throw_notfoundexception_when_entryId_is_wrong() {
        var entryId = UUID.randomUUID();
        var jobId = UUID.randomUUID();
        var job = new Job(jobId, entryId, "employee", OffsetDateTime.now());
        doReturn(Optional.of(job)).when(jobRepository)
                .findById(jobId);
        doReturn(false).when(entryRepository)
                .existsByIdAndCompanyId(entryId, "employee");


        assertThrows(NotFoundException.class, () -> jobService.rateEmployee(jobId, "3,5", "employee"));
    }


}