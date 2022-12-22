package com.kodizim.findaduck.domain.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

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
    Optional<ApplicationDto> getApplicationDto(String userId);
}
