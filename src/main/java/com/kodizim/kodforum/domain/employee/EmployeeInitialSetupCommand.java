package com.kodizim.kodforum.domain.employee;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
