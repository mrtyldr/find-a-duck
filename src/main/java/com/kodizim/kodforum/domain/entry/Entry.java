package com.kodizim.kodforum.domain.entry;

import com.kodizim.kodforum.domain.employee.Profession;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Entry extends AbstractAggregateRoot<Entry> {

    @Id
    private UUID id;

    private UUID categoryId;

    private UUID employerId;

    private String title;

    private String content;

    private BigDecimal hourlyPay;

    private OffsetDateTime validTil;

    private OffsetDateTime createdOn;

    private OffsetDateTime modifiedOn;

    @Type(type = "list-array")
    @Column(columnDefinition = "uuid[]")
    private List<UUID> expectedProfessions = new ArrayList<>();

}