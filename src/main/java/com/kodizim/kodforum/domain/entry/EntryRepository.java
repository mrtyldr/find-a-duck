package com.kodizim.kodforum.domain.entry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntryRepository extends JpaRepository<Entry, UUID> {

    boolean existsByCompanyIdAndId(String userId, UUID entryId);
}
