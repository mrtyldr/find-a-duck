package com.kodizim.kodforum.domain.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Job extends AbstractAggregateRoot<Job> {

    @Id
    private UUID jobId;

    private UUID entryId;

    private String employeeId;

    private Integer employeeRating;

    private Integer companyRating;

}
