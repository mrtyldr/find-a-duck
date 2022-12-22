package com.kodizim.findaduck.domain.job;

import java.time.OffsetDateTime;

public record ApplicationDto(String entryTitle,
                             OffsetDateTime appliedOn,
                             ApplicationStatus status) {
}
