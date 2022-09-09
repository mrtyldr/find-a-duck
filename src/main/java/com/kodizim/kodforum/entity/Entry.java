package com.kodizim.kodforum.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Entry {

    @Id
    UUID id;

    UUID categoryId;

    String title;

    String content;

    OffsetDateTime createdOn;

    OffsetDateTime modifiedOn;


}
