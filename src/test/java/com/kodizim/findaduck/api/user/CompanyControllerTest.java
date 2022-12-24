package com.kodizim.findaduck.api.user;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.application.Entry.EntryService;
import com.kodizim.findaduck.application.TestDataService;
import com.kodizim.findaduck.application.user.CompanyService;
import com.kodizim.findaduck.application.user.EmployeeService;
import com.kodizim.findaduck.domain.entry.EntryRepository;
import com.kodizim.findaduck.domain.job.ApplicationRepository;
import com.kodizim.findaduck.domain.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
class CompanyControllerTest extends BaseTestClass {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyService companyService;
    @Autowired
    JobRepository jobRepository;
    @Test
    @WithMockUser(authorities = "STANDARD", username = "company")
    void acceptApplication() throws Exception {
        var request = post("/api/company/accept-application/{applicationId}", application.getApplicationId());
        mockMvc.perform(request).andExpect(status().isNoContent());
        assertThat(jobRepository.findAll()).isNotEmpty();
        assertThat(jobRepository.findByEmployeeId("employee")).isNotEmpty();
    }
    @Test
    @WithMockUser(authorities = "STANDARD", username = "company")
    void should_get_companyDto() throws Exception{
        var request = get("/api/company");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "result":{
                        "companyId":"company",
                        "companyName":"testCompany",
                        "about":"we are hiring for test",
                        "phoneNumber":"1231231231",
                        "email":"company@company.com",
                        "photoLocationKey":"/home",
                        "city":null
                        }
                        }
                        
                        """,true));
    }

}