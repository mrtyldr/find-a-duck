package com.kodizim.findaduck.domain.entry;

import java.util.List;

public interface EntryQueries {
    List<EntryDto> getEntryDtoForCompany(String companyId);
    List<EntryDto> getEntryDto(String employeeId, String professions);
    List<EntryDto> entrySearchEmployee(String searchString);
    List<EntryDto> entrySearchCompany(String searchString, String userId);
}
