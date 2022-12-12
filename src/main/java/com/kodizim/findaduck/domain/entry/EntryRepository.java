package com.kodizim.findaduck.domain.entry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, UUID> {

    boolean existsByCompanyIdAndId(String userId, UUID entryId);

    List<Entry> findByCompanyId(String company);

    boolean existsByIdAndCompanyId(UUID entryId, String userId);

}
