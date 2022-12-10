package com.kodizim.findaduck.domain.employee;

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
                e.professions
                )
                from Employee  e
                where e.employeeId = :userId
            """)
    Optional<EmployeeDto> getEmployeeDto(String userId);

   /* @Query("""
            select p.professionName from Profession p where p.professionId in (:professionIds)
            """)
    List<String> getProfessionName(List<UUID> professionIds);*/

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