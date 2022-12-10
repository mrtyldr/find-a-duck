package com.kodizim.findaduck.domain.job;

import lombok.Value;

import java.util.UUID;
@Value
public class ApplicationAccepted {
    UUID entryId;
    String employeeId;
}