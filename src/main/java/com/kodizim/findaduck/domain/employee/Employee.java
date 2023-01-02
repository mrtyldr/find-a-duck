package com.kodizim.findaduck.domain.employee;

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
import java.util.List;


@Entity
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AbstractAggregateRoot<Employee> {

    @Id
    private String employeeId;

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;

    private String photoLocationKey;

    private LocalDate birthDate;

    private String about;

    @Type(type = "list-array")
    @Column(name = "professions",columnDefinition = "String[]")
    private List<String> professions = new ArrayList<>();

    public Employee(String employeeId, String email) {
        this.employeeId = employeeId;
        this.email = email;
    }

    public void employeeInitial(String name,
                    String surname,
                    String phoneNumber,
                    String photoLocationKey,
                    LocalDate birthDate,
                    String about,
                    List<String> professions) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.photoLocationKey = photoLocationKey;
        this.birthDate = birthDate;
        this.about = about;
        this.professions = professions;
    }
}
