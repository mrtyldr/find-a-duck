package com.kodizim.findaduck.domain.employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeQueries {
    List<String> getProfessionName(List<UUID> professionIds);
}
