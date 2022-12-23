package com.kodizim.findaduck.application.Entry;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.domain.entry.Advertisement;
import com.kodizim.findaduck.domain.entry.Category;
import com.kodizim.findaduck.domain.entry.EntryDto;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
@SpringBootTest
@ActiveProfiles("test")
class EntryServiceTest{

    @Autowired
    EntryService entryService;
    @MockBean
    EntryRepository entryRepository;



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
        Mockito.doReturn(List.of(entryDto)).when(entryRepository).getEntryDto("employee");
        var advertisement = entryService.getAdvertisements("employee").stream().findFirst()
                .orElseThrow();
        assertThat(advertisement.companyName()).isEqualTo("company");
        assertThat(advertisement.entryId()).isEqualTo(entryDto.getEntryId());
    }
}