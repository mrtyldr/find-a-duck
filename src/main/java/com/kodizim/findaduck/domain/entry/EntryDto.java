package com.kodizim.findaduck.domain.entry;

import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;
@Value
public class EntryDto {
    UUID entryId;
    String companyName;
    String title;
    String content;
    Category category;
    BigDecimal hourlyPay;
    OffsetDateTime createdOn;
    OffsetDateTime jobStartDate;
    OffsetDateTime validTil;
    List<String> expectedProfessions;

    public EntryDto(UUID entryId, String companyName, String title, String content, Category category, BigDecimal hourlyPay, OffsetDateTime createdOn, OffsetDateTime jobStartDate, OffsetDateTime validTil, Object expectedProfessionIds) {
        this.entryId = entryId;
        this.companyName = companyName;
        this.title = title;
        this.content = content;
        this.category = category;
        this.hourlyPay = hourlyPay;
        this.createdOn = createdOn;
        this.jobStartDate = jobStartDate;
        this.validTil = validTil;
        this.expectedProfessions = (List<String>) expectedProfessionIds;
    }
}
