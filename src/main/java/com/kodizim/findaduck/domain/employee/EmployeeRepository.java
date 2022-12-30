package com.kodizim.findaduck.domain.employee;

import com.kodizim.findaduck.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, EmployeeQueries {
    Optional<Employee> findByEmployeeId(String userId);

    @Query("""
                select new com.kodizim.findaduck.domain.employee.EmployeeDto(
                e.employeeId,
                e.name,
                e.surname,
                e.phoneNumber,
                e.photoLocationKey,
                e.birthDate,
                e.about,
                e.professions,
                (select AVG(j.employeeRating) from Job j where j.employeeId = :userId)          
                )
                from Employee  e
                where e.employeeId = :userId    
            """)
    Optional<EmployeeDto> getEmployeeDto(String userId);

    @Query("""
           select new com.kodizim.findaduck.domain.UserInfo(
           e.employeeId,
           (case when e.name is not null then true else false end)
           )
           from Employee e where e.employeeId = :id
"""
    )
    Optional<UserInfo> isOnboardingDone(String id);

    boolean existsByEmployeeId(String id);

    @Component
    @RequiredArgsConstructor
    class EmployeeQueriesImpl implements EmployeeQueries {

        private final EntityManager entityManager;

        @Override
        public List<String> getProfessionName(List<UUID> professionIds) {
            return entityManager
                    .createQuery("select p.professionName from Profession p where p.professionId in (:professionIds)", String.class)
                    .setParameter("professionIds", professionIds)
                    .getResultList();
        }

    }
}