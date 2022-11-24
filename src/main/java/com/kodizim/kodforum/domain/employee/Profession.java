package com.kodizim.kodforum.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Profession extends AbstractAggregateRoot<Profession> {
    @Id
    private UUID professionId;

    private String professionName;
}
