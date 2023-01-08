package com.kodizim.findaduck.service.Entry;

import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.entry.Category;
import com.kodizim.findaduck.domain.entry.EntryDto;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class EntryServiceTest{



    @Mock
    EntryRepository entryRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    ApplicationRepository applicationRepository;


    @Test
    void should_get_advertisements() {
        EntryService entryService = new EntryService(
                entryRepository,
                null,null,employeeRepository,
                applicationRepository,null
        );
        EntryDto entryDto = new EntryDto(
                UUID.randomUUID(),
                "company",
                "TestJob",
                "Testing jobs",
                Category.IT,
                new BigDecimal("13"),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                List.of()
        );
        Mockito.doReturn(List.of(entryDto)).when(entryRepository).getEntryDto("employee", "[java, Spring Boot, PostgreSQL]");
        Mockito.doReturn(List.of("java","Spring Boot","PostgreSQL")).when(employeeRepository).getProfessions("employee");
        var advertisement = entryService.getAdvertisements("employee").stream().findFirst()
                .orElseThrow();
        assertThat(advertisement.companyName()).isEqualTo("company");
        assertThat(advertisement.entryId()).isEqualTo(entryDto.getEntryId());
    }
}