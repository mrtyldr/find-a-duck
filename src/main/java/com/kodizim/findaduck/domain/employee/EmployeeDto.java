package com.kodizim.findaduck.domain.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    String employeeId;

    String name;

    String surname;

    String phoneNumber;

    String photoLocationKey;

    LocalDate birthDate;

    String about;
    @JsonIgnore
    List<UUID> professionIds;

    List<String> professions;

    Boolean onboardingDone;

    Double rating;

    public EmployeeDto(String employeeId, String name,
                       String surname, String phoneNumber,
                       String photoLocationKey, LocalDate birthDate,
                       String about, Object professionIds, Double rating) {
        this.employeeId = employeeId;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.photoLocationKey = photoLocationKey;
        this.birthDate = birthDate;
        this.about = about;
        this.professionIds = (List<UUID>) professionIds;
        this.onboardingDone = this.name != null;
        this.rating = rating;
    }

}
