package com.kodizim.findaduck.domain.employee;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record UpdateEmployeeCommand(String phoneNumber,
                                    String photoLocationKey,
                                    @NotNull LocalDate birthDate,
                                    String about,
                                    List<String> professions) {
}
