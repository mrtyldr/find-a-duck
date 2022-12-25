package com.kodizim.findaduck.domain.job;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicationDto(UUID applicationId,
                             String entryTitle,
                             String employeeName,
                             String employeeSurname,
                             OffsetDateTime appliedOn,
                             ApplicationStatus status) {
}
