package com.kodizim.kodforum.domain.entry;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class AddEntryCommand {
    @NotNull
    Category category;

    String title;

    String content;
    @NotNull
    BigDecimal hourlyPay;
    @NotNull
    OffsetDateTime jobStartDate;
    @NotNull
    OffsetDateTime validTil;

    List<String> expectedProfessions;
}
