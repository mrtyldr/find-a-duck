package com.kodizim.findaduck.domain.job;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicationDto(UUID applicationId,
                             String entryTitle,
                             OffsetDateTime appliedOn,
                             ApplicationStatus status) {
}
