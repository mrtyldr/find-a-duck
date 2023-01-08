package com.kodizim.findaduck.domain.employee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public record EmployeeInitialSetupCommand(@NotNull @NotBlank String name,
                                          @NotNull @NotBlank String surname,
                                          String phoneNumber,
                                          String photoLocationKey,
                                          @NotNull LocalDate birthDate,
                                          String about,
                                          List<String> professions) {
}
