package com.kodizim.findaduck.domain.entry;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


public record AddEntryCommand(@NotNull Category category,
                              String title,
                              String content,
                              @NotNull BigDecimal hourlyPay,
                              @NotNull OffsetDateTime jobStartDate,
                              @NotNull OffsetDateTime validTil,
                              List<String> expectedProfessions) {
}
