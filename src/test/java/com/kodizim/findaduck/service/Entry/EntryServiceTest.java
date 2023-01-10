package com.kodizim.findaduck.service.Entry;

import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.employee.Employee;
import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.employee.ProfessionRepository;
import com.kodizim.findaduck.domain.entry.Category;
import com.kodizim.findaduck.domain.entry.EntryDto;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.error.AlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EntryServiceTest{



    @Mock
    EntryRepository entryRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    CompanyRepository companyRepository;
    @Mock
    ProfessionRepository professionRepository;

    @Mock
    Clock clock;
    @InjectMocks
    EntryService entryService;



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
        Mockito.doReturn(List.of(entryDto)).when(entryRepository).getEntryDto("employee", "java |  | Spring | Boot |  | PostgreSQL");
        Mockito.doReturn(List.of("java","Spring Boot","PostgreSQL")).when(employeeRepository).getProfessions("employee");
        var advertisement = entryService.getAdvertisements("employee").stream().findFirst()
                .orElseThrow();
        assertThat(advertisement.companyName()).isEqualTo("company");
        assertThat(advertisement.entryId()).isEqualTo(entryDto.getEntryId());
    }

    @Test
    void should_throw_alreadyexistsexception_when_an_application_already_exists(){
        Employee employee = new Employee("employee","employee@mail.com");
        Mockito.doReturn(Optional.of(employee)).when(employeeRepository).findByEmployeeId("employee");
        UUID entryId = UUID.randomUUID();
        Mockito.doReturn(true).when(applicationRepository).existsByEntryIdAndEmployeeId(entryId,"employee");
        assertThrows(AlreadyExistsException.class, () -> entryService.apply(entryId,"employee") );
    }
}