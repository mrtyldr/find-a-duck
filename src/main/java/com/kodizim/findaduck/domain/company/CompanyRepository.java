package com.kodizim.findaduck.domain.company;

import com.kodizim.findaduck.domain.UserInfo;
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
    @Query("""
     select new com.kodizim.findaduck.domain.UserInfo(
           c.companyId,
           (select case when cmp.companyName is not null then true else false end
           from Company cmp  where cmp.companyId = :id
           )
           )
           from Company c where c.companyId = :id
""")
    Optional<UserInfo>isOnboardingDone(String id);
}
