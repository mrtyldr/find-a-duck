package com.kodizim.kodforum.domain.job;

import lombok.Value;

import java.util.UUID;
@Value
public class ApplicationAccepted {
    UUID entryId;
    String employeeId;
}
