package com.kodizim.kodforum.domain.employee;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


@Entity
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AbstractAggregateRoot<Employee> {

    @Id
    private UUID employeeId;

    private String userId;

    private String name;

    private String surname;

    private String phoneNumber;

    private String photoLocationKey;

    private LocalDate birthDate;

    private String about;

    @Type(type = "list-array")
    @Column(name = "professions",columnDefinition = "uuid[]")
    private List<UUID> professions = new ArrayList<>();
}
