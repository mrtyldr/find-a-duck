package com.kodizim.kodforum.domain.entry;

import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class AddEntryCommand {
    UUID categoryId;

    UUID employerId;

    String title;

    String content;

    BigDecimal hourlyPay;

    OffsetDateTime validTil;
    List<UUID> expectedProfessions;
}
