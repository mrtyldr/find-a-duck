package com.kodizim.kodforum.domain.job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    Optional<Application> findByEmployeeId(String employeeId);
}
