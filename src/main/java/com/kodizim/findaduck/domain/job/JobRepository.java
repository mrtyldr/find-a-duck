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
                        c.companyName,
                        j.employeeId,
                        j.startDate,
                        e.title,
                        e.content,
                        j.companyRating
            )
            from Job j
                inner join Entry e on j.entryId = e.id
                inner join Company c on e.companyId = c.companyName
                where j.employeeId = :employeeId
            """)
    List<JobDto> getJobDtoByEmployeeId(String employeeId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.JobDto(
                        j.jobId,
                        c.companyName,
                        j.employeeId,
                        j.startDate,
                        e.title,
                        e.content,
                        j.employeeRating
            )
            from Job j
                inner join Entry e on j.entryId = e.id
                inner join Company c on e.companyId = c.companyId
                where e.companyId = :companyId
            """)
    List<JobDto> getJobDtosForCompany(String companyId);
}
