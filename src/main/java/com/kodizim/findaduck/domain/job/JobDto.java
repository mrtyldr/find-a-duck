package com.kodizim.findaduck.domain.job;

import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Value
public class JobDto {
    UUID jobId;
    String companyName;
    String employeeId;
    OffsetDateTime startDate;
    String title;
    String content;

    BigDecimal rating;
}
