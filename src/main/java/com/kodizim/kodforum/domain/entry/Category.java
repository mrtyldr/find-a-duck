package com.kodizim.kodforum.domain.entry;


import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;


public enum Category {
    FINANCE, BUSINESS_MANAGEMENT, ART,
    ENGINEERING, HEALTH, HOSPITALITY, IT,
    LAW, SPORT, MARKETING, MEDIA, CONSTRUCTION,
    HR, RETAIL, SALES, EDUCATION, LOGISTICS
}
