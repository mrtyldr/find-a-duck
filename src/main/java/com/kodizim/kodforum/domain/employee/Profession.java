package com.kodizim.kodforum.domain.employee;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Profession extends AbstractAggregateRoot<Profession> {
    @Id
    private UUID professionId;

    private String professionName;
}
