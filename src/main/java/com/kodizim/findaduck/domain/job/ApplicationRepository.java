package com.kodizim.findaduck.domain.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    Optional<Application> findByEmployeeId(String employeeId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.ApplicationDto(
                e.title,
                a.appliedOn,
                a.status
            )
                from Application a
                inner join Entry e on a.entryId = e.id
                where a.employeeId = :userId
            """)
    List<ApplicationDto> getApplicationDto(String userId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.ApplicationDto(
                e.title,
                a.appliedOn,
                a.status
            )
                from Application a
                inner join Entry e on a.entryId = e.id
                where e.companyId = :companyUserId
            """)
    List<ApplicationDto> getCompanyApplicationDto(String companyUserId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.ApplicationDto(
                e.title,
                a.appliedOn,
                a.status
            )
                from Entry e
                left join Application a on e.id = a.entryId
                where e.companyId = :companyUserId and e.id = :entryId
            """)
    List<ApplicationDto> getEntryApplications(UUID entryId, String companyUserId);

    Optional<Application> findByEmployeeIdAndEntryId(String userId, UUID entryId);
}
