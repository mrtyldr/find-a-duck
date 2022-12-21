package com.kodizim.findaduck.application.Entry;


import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.JobDto;
import com.kodizim.findaduck.domain.job.JobRepository;
import com.kodizim.findaduck.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final EntryRepository entryRepository;

    public void rateEmployee(UUID jobId, Integer rate, String userId) {
        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        if(!entryRepository.existsByIdAndCompanyId(job.getEntryId(),userId))
            throw new NotFoundException("job not found");
        job.rateEmployee(rate);
        jobRepository.save(job);
    }

    public void rateCompany(UUID jobId, Integer rate, String userId) {
        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        if(!job.getEmployeeId().equals(userId))
            throw new NotFoundException("job not found");
        job.rateCompany(rate);

        jobRepository.save(job);
    }


    public List<JobDto> getEmployeeJobs(String employeeId) {
        return jobRepository.getJobDtoByEmployeeId(employeeId);
    }

    public List<JobDto> getCompanyJobs(String companyId) {
        return jobRepository.getJobDtosForCompany(companyId);
    }

    public void rateEmployeeForTesting(String userId, int rate) {

    }
}
