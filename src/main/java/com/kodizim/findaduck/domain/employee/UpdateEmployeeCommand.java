package com.kodizim.findaduck.domain.employee;

import java.time.LocalDate;
import java.util.List;

public record UpdateEmployeeCommand(String phoneNumber,
                                    String photoLocationKey,
                                    LocalDate birthDate,
                                    String about,
                                    List<String> professions) {
}
