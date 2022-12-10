package com.kodizim.findaduck.domain.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Job extends AbstractAggregateRoot<Job> {

    @Id
    private UUID jobId;

    private UUID entryId;

    private String employeeId;

    OffsetDateTime startDate;

    private Integer employeeRating;

    private Integer companyRating;

    public Job(UUID jobId, UUID entryId, String employeeId,OffsetDateTime startDate) {
        this.jobId = jobId;
        this.entryId = entryId;
        this.employeeId = employeeId;
        this.startDate = startDate;
    }
}