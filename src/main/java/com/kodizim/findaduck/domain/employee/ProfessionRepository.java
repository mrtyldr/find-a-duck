package com.kodizim.findaduck.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession, UUID> {

    boolean existsByProfessionName(String professionName);

    @Query("""
        select p.professionId
         from Profession p
         where p.professionName in (:professions)
""")
    List<UUID> findProfessionsByName(List<String> professions);
}
