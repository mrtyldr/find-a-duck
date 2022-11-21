package com.kodizim.kodforum.domain.company;

import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class Company extends AbstractAggregateRoot<Company> {
    @Id
    private UUID companyId;

    private String userId;

    private String companyName;

    private String phoneNumber;

    private String about;

    private String photoLocationKey;

}
