package com.kodizim.findaduck.domain.entry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, UUID> {

    boolean existsByCompanyIdAndId(String userId, UUID entryId);

    List<Entry> findByCompanyId(String company);

    boolean existsByIdAndCompanyId(UUID entryId, String userId);

    @Query("""
        select new com.kodizim.findaduck.domain.entry.EntryDto(
        e.id,
        c.companyName,
        e.title,
        e.content,
        e.category,
        e.hourlyPay,
        e.createdOn,
        e.jobStartDate,
        e.validTil,
        e.expectedProfessions 
        )
        from Entry e
        inner join Company c on e.companyId = c.companyId
""")
    List<EntryDto> getEntryDto(String employeeId);
}
