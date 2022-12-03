package com.kodizim.kodforum.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByCompanyId(String userId);

    boolean existsByCompanyId(String UserId);
}
