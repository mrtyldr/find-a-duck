package com.kodizim.kodforum.domain.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.array.UUIDArrayType;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByUserId(String userId);

    @Query("""
    select new com.kodizim.kodforum.domain.employee.EmployeeDto(
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
    where e.userId = :userId
""")
    Optional<EmployeeDto> getEmployeeDto(String userId);

    @Query("""
select p.professionName from Profession p where p.professionId in (:professionIds)
""")
    List<String> getProfessionName(List<UUID> professionIds);

}