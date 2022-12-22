package com.kodizim.findaduck.api;

import com.kodizim.findaduck.BaseTestClass;

import com.kodizim.findaduck.application.TestDataService;
import com.kodizim.findaduck.domain.company.CompanyRepository;
import com.kodizim.findaduck.domain.entry.EntryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    @Test
    @WithMockUser(authorities = "STANDARD",value="company")
    void should_add_Entry() throws Exception {
        var validTil = OffsetDateTime.of(LocalDateTime.of(2022, 12, 25, 17, 0), ZoneOffset.of("+3"));

        var request = post("/api/entry/")
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

        mockMvc.perform(get("/api/entry/"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                     {
                                     "result":[
                                     {
                                     "id":"%s",
                                     "category":"IT",
                                     "companyId":"company",
                                     "title":"Looking for an IT guy",
                                     "content":"IT guy",
                                     "hourlyPay":12,
                                     "status":"ACTIVE"
                                     },
                                     {
                                     "category":"FINANCE",
                                     "companyId":"%s"
                                     ,"title":"Garson Aranıyor",
                                     "content":"ufak cafemize garson arıyoz saati 3 lira",
                                     "hourlyPay":3.00,
                                     "validTil":"2022-12-25T17:00:00+03:00"
                                     }]
                                     }
                        """.formatted(entry.getId(),company.getCompanyId())));


    }



}