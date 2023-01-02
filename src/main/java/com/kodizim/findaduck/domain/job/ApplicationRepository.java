package com.kodizim.findaduck.domain.job;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.OffsetDateTimeType;
import org.hibernate.type.UUIDCharType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

public interface ApplicationRepository extends JpaRepository<Application, UUID>, ApplicationQueries {
    Optional<Application> findByEmployeeId(String employeeId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.ApplicationDto(
                a.applicationId,
                e.title,
                emp.name,
                emp.surname,      
                a.appliedOn,
                a.status
            )
                from Application a
                inner join Entry e on a.entryId = e.id
                inner join Employee emp on a.employeeId = emp.employeeId
                where a.employeeId = :userId
            """)
    List<ApplicationDto> getApplicationDto(String userId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.ApplicationDto(
                a.applicationId,
                e.title,
                emp.name,
                emp.surname,
                a.appliedOn,
                a.status
            )
                from Application a
                inner join Entry e on a.entryId = e.id
                inner join Employee emp on a.employeeId = emp.employeeId
                where e.companyId = :companyUserId and a.status = 'WAITING'
            """)
    List<ApplicationDto> getCompanyApplicationDto(String companyUserId);

    @Query("""
            select new com.kodizim.findaduck.domain.job.ApplicationDto(
                a.applicationId,
                e.title,
                emp.name,
                emp.surname,
                a.appliedOn,
                a.status
            )
                from Entry e
                left join Application a on e.id = a.entryId
                inner join Employee emp on a.employeeId = emp.employeeId
                where e.companyId = :companyUserId and e.id = :entryId
                and a.status = 'WAITING'
            """)
    List<ApplicationDto> getEntryApplications(UUID entryId, String companyUserId);

    Optional<Application> findByEmployeeIdAndEntryId(String userId, UUID entryId);

    boolean existsByEntryIdAndEmployeeId(UUID entryId, String employeeId);

    @RequiredArgsConstructor
    class ApplicationQueriesImpl implements ApplicationQueries {
        private final EntityManager entityManager;

        @Override
        public List<ApplicationDto> getEntryApplications(UUID entryId, String companyUserId, String professions) {
            var sql = """
                    select
                    a.application_id,
                    e.title,
                    emp.name,
                    emp.surname,
                    a.applied_on,
                    a.status
                    from Entry e
                    left join Application a on e.id = a.entry_id
                    inner join Employee emp on a.employee_id = emp.employee_id
                    where e.company_id = :companyUserId and e.id = :entryId
                    and a.status = 'WAITING'
                    order by ts_rank(to_tsvector(array_to_string(e.expected_professions,' ')), plainto_tsquery(:professions)) desc
                        """;
            var professions1 = (List<Tuple>) entityManager.createNativeQuery(sql, Tuple.class).setParameter("professions", professions)
                    .unwrap(NativeQuery.class)
                    .addScalar("application_id", UUIDCharType.INSTANCE)
                    .addScalar("title")
                    .addScalar("name")
                    .addScalar("surname")
                    .addScalar("applied_on", OffsetDateTimeType.INSTANCE)
                    .addScalar("status")
                    .getResultList();
            return professions1.stream().map(t -> toApplicationDto(t)).collect(Collectors.toList());

        }

        private ApplicationDto toApplicationDto(Tuple tuple) {
            var status = ApplicationStatus.valueOf(tuple.get("status", String.class));
            return new ApplicationDto(
                    tuple.get("application_id", UUID.class),
                    tuple.get("title", String.class),
                    tuple.get("name", String.class),
                    tuple.get("surname", String.class),
                    tuple.get("applied_on", OffsetDateTime.class),
                    status
            );
        }
    }
}
