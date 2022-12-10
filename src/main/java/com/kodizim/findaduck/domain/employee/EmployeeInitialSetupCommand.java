package com.kodizim.findaduck.domain.employee;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class EmployeeInitialSetupCommand {
    String name;

    String surname;

    String phoneNumber;

    String photoLocationKey;

    LocalDate birthDate;

    String about;

    List<String> professions;
}
