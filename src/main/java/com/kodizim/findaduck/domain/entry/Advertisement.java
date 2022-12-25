package com.kodizim.findaduck.domain.entry;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;

public record Advertisement(UUID entryId,
                            String companyName,
                            Category category,
                            BigDecimal hourlyPay,
                            String title,
                            String content,
                            OffsetDateTime jobStartDate,
                            OffsetDateTime validTil,
                            OffsetDateTime createdOn,
                            boolean isApplied,
                            List<String> expectedProfessions
                            ) {
}
