package com.kodizim.kodforum.domain.entry;


import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
public class Category extends AbstractAggregateRoot<Category> {

    @Id
    private UUID id;

    private String name;

}
