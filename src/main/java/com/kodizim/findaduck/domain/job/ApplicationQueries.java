package com.kodizim.findaduck.domain.job;

import java.util.List;
import java.util.UUID;

public interface ApplicationQueries {
    List<ApplicationDto> getEntryApplications(UUID entryId, String companyUserId,String Professions);
}
