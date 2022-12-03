package com.kodizim.kodforum.domain.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.UUID;

import static com.kodizim.kodforum.domain.job.ApplicationStatus.*;

@Entity
@Getter
@NoArgsConstructor
public class Application extends AbstractAggregateRoot<Application> {

    @Id
    private UUID applicationId;

    private UUID entryId;

    private String employeeId;

    private OffsetDateTime appliedOn;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Application(UUID entryId, String employeeId, OffsetDateTime appliedOn) {
        this.applicationId = UUID.randomUUID();
        this.entryId = entryId;
        this.employeeId = employeeId;
        this.appliedOn = appliedOn;
        this.status = WAITING;
    }

    public void accept() {
        this.status = ACCEPTED;
        registerEvent(new ApplicationAccepted(this.entryId, this.employeeId));
    }

    public void reject() {
        this.status = REJECTED;
    }
}
