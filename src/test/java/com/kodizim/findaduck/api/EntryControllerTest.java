package com.kodizim.findaduck.api;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.service.TestDataService;
import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableSpringDataWebSupport
class EntryControllerTest extends BaseTestClass {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    TestDataService testDataService;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    @WithMockUser(authorities = "STANDARD", value = "company")
    void should_add_Entry() throws Exception {
        var validTil = OffsetDateTime.of(LocalDateTime.of(2022, 12, 25, 17, 0), ZoneOffset.of("+3"));

        var request = post("/api/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                                    {
                                                    "category": "FINANCE",
                                                    "title": "Garson Aranıyor",
                                                    "content": "ufak cafemize garson arıyoz saati 3 lira",
                                                    "hourlyPay": "3.00",
                                                    "validTil": "%s",
                                                    "expectedProfessions" : ["garson"]
                                              
                                                    }
                        """.formatted(validTil));
        mockMvc.perform(request).andExpect(status().isNoContent());
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "entry")).isGreaterThan(0);
    }

    @Test
    @WithMockUser(authorities = "STANDARD", value = "company")
    void should_update_entry() throws Exception {
        var request = put("/api/entry/{entryId}", entry.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "category": "IT",
                            "title": "title değişti",
                            "content": "content değişti",
                            "hourlyPay": "40",
                            "expectedProfessions" : ["bu da Değişti"],
                            "validTil": "%s",
                            "jobStartDate" : "%s"
                        }
                        """.formatted(OffsetDateTime.now(), OffsetDateTime.now()));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        var updatedEntry = entryRepository.findById(entry.getId())
                .orElseThrow();
        assertThat(updatedEntry.getContent()).isEqualTo("content değişti");
        assertThat(updatedEntry.getTitle()).isEqualTo("title değişti");
    }

    @Test
    @WithMockUser(authorities = "STANDARD", value = "employee")
    void should_return_most_relevant_entries_ranked() throws Exception {
        var developer = testDataService.addEntryDeveloper();
        var barista = testDataService.addEntryBarista();
        var coffeeMaker = testDataService.addEntryAnotherBarista();

        //WHEN
        var request = mockMvc.perform(get("/api/entry/search")
                .param("query","barmen barista coffee"));



        //should get barista first
        request.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "result": [{

                        "entryId": "%s",
                        "companyName": "testCompany",
                        "category" : "IT",
                        "hourlyPay": 12,
                        "title": "We need a Barista guy",
                        "content": "A need for barista guy",
                        "expectedProfessions": ["COFFEE", "TEA","BARISTA"]

                        },
                        {
                         "entryId": "%s",
                        "companyName": "testCompany",
                        "category" : "IT",
                        "hourlyPay": 12,
                        "title": "We need a guy to make us some coffee",
                        "content": "A need for coffee maker guy",
                        "expectedProfessions": ["COFFEE", "TEA", "BARISTA"]
                        }
                        ]
                        }
                        """.formatted(barista.getId(), coffeeMaker.getId())));
        //when
        var request2 = mockMvc.perform(get("/api/entry/search").queryParam("query", "coffee maker barista"));


        //should get coffer maker first
        request2.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "result": [
                        {
                         "entryId": "%s",
                        "companyName": "testCompany",
                        "category" : "IT",
                        "hourlyPay": 12,
                        "title": "We need a guy to make us some coffee",
                        "content": "A need for coffee maker guy",
                        "expectedProfessions": ["COFFEE", "TEA", "BARISTA"]
                        },
                        {

                        "entryId": "%s",
                        "companyName": "testCompany",
                        "category" : "IT",
                        "hourlyPay": 12,
                        "title": "We need a Barista guy",
                        "content": "A need for barista guy",
                        "expectedProfessions": ["COFFEE", "TEA","BARISTA"]

                        }
                        ]
                        }
                        """.formatted(coffeeMaker.getId(), barista.getId())));


    }


}