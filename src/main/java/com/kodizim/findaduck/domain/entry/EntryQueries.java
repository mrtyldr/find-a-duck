package com.kodizim.findaduck.domain.entry;

import java.util.List;

public interface EntryQueries {
    List<EntryDto> getEntryDtoForCompany(String companyId);
    List<EntryDto> getEntryDto(String employeeId, String professions);
}
