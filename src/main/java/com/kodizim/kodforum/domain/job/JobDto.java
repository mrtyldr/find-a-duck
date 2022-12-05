package com.kodizim.kodforum.domain.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value
public class JobDto {
    UUID jobId;
    String companyId;
    String employeeId;
    OffsetDateTime startDate;
    String title;
    String content;
}
