package com.kodizim.findaduck.domain.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    List<Job> findByEmployeeId(String employeeId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.JobDto(
                        j.jobId,
                        e.companyId,
                        j.employeeId,
                        j.startDate,
                        e.title,
                        e.content
            )
            from Job j
                inner join Entry e on j.entryId = e.id
                where j.employeeId = :employeeId
            """)
    List<JobDto> getJobDtoByEmployeeId(String employeeId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.JobDto(
                        j.jobId,
                        e.companyId,
                        j.employeeId,
                        j.startDate,
                        e.title,
                        e.content
            )
            from Job j
                inner join Entry e on j.entryId = e.id
                where e.companyId = :companyId
            """)
    List<JobDto> getJobDtosForCompany(String companyId);
}
