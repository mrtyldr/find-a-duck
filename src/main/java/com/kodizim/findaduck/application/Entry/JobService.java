package com.kodizim.findaduck.application.Entry;


import com.kodizim.findaduck.domain.job.JobDto;
import com.kodizim.findaduck.domain.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;


    public List<JobDto> getEmployeeJobs(String employeeId) {
        return jobRepository.getJobDtoByEmployeeId(employeeId);
    }
}
