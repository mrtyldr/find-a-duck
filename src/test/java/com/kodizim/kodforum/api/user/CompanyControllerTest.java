package com.kodizim.kodforum.api.user;

import com.kodizim.kodforum.BaseTestClass;
import com.kodizim.kodforum.application.Entry.EntryService;
import com.kodizim.kodforum.application.TestDataService;
import com.kodizim.kodforum.application.user.CompanyService;
import com.kodizim.kodforum.application.user.EmployeeService;
import com.kodizim.kodforum.domain.entry.EntryRepository;
import com.kodizim.kodforum.domain.job.ApplicationRepository;
import com.kodizim.kodforum.domain.job.JobRepository;
import lombok.RequiredArgsConstructor;
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
    EmployeeService employeeService;
    @Autowired
    CompanyService companyService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    EntryService entryService;
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    TestDataService testDataService;

    @Test
    @WithMockUser(authorities = "STANDARD",username = "company")
    void acceptApplication() throws Exception {
        var applicationId = testDataService.addEntryAndApplication();
        var request = post("/api/company/accept-application/{applicationId}",applicationId);
        mockMvc.perform(request).andExpect(status().isNoContent());
        assertThat(jobRepository.findAll()).isNotEmpty();
        assertThat(jobRepository.findByEmployeeId("employee")).isNotEmpty();
        cleanBeforeAndAfter("entry","job");
    }
}