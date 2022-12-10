package com.kodizim.findaduck.domain.entry;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Entry extends AbstractAggregateRoot<Entry> {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String companyId;

    private String title;

    private String content;

    private BigDecimal hourlyPay;

    private OffsetDateTime jobStartDate;
    private OffsetDateTime validTil;

    private OffsetDateTime createdOn;

    private OffsetDateTime modifiedOn;
    @Enumerated(EnumType.STRING)
    private EntryStatus status;

    @Type(type = "list-array")
    @Column(columnDefinition = "uuid[]")
    private List<UUID> expectedProfessions = new ArrayList<>();

    public Entry(UUID id,
                 Category category,
                 String companyId,
                 String title,
                 String content,
                 BigDecimal hourlyPay,
                 OffsetDateTime jobStartDate,
                 OffsetDateTime validTil,
                 OffsetDateTime createdOn,
                 OffsetDateTime modifiedOn,
                 List<UUID> expectedProfessions) {
        this.id = id;
        this.category = category;
        this.companyId = companyId;
        this.title = title;
        this.content = content;
        this.hourlyPay = hourlyPay;
        this.jobStartDate = jobStartDate;
        this.validTil = validTil;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.expectedProfessions = expectedProfessions;
        this.status = EntryStatus.ACTIVE;
    }

    public void entryClosed(){
        this.status = EntryStatus.CLOSED;
    }
}
