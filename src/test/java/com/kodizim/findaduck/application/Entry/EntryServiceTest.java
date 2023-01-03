package com.kodizim.findaduck.application.Entry;

import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.entry.Category;
import com.kodizim.findaduck.domain.entry.EntryDto;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
@ActiveProfiles("test")
class EntryServiceTest{

    @Autowired
    EntryService entryService;
    @MockBean
    EntryRepository entryRepository;
    @MockBean
    EmployeeRepository employeeRepository;



    @Test
    void should_get_advertisements() {
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