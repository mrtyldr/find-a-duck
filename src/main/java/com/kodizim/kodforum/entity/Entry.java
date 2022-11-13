package com.kodizim.kodforum.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Entry extends AbstractAggregateRoot<Entry> {

    @Id
    private UUID id;

    private UUID categoryId;

    private String title;

    private String content;

    private BigDecimal hourlyPay;

    private OffsetDateTime validTil;

    private OffsetDateTime createdOn;

    private OffsetDateTime modifiedOn;


}
