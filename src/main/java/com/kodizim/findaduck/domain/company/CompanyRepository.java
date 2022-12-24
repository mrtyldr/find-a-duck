package com.kodizim.findaduck.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByCompanyId(String userId);

    boolean existsByCompanyId(String UserId);
    @Query("""
        select new com.kodizim.findaduck.domain.company.CompanyDto(
        c.companyId,
        c.companyName,
        c.about,
        c.phoneNumber,
        c.email,
        c.photoLocationKey,
        c.city
        )
        from Company c
        where c.companyId = :companyId
""")
    Optional<CompanyDto> getCompanyDto(String companyId);
}
