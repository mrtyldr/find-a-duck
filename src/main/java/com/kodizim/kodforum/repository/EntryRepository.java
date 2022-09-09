package com.kodizim.kodforum.repository;

import com.kodizim.kodforum.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
}
