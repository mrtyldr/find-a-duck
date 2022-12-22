package com.kodizim.findaduck.api.user;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.application.TestDataService;
import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.employee.ProfessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class EmployeeControllerTest extends BaseTestClass {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProfessionRepository professionRepository;
    @Autowired
    TestDataService testDataService;



    @WithMockUser(authorities = "STANDARD", value = "employee")
    @Test
    void should_add_employee_and_professions() throws Exception {
        var request = post("/api/employee/initial-setup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name" : "Murat",
                            "surname" : "YILDIRIM",
                            "phoneNumber" : "123123123",
                            "photoLocationKey" : "/ev",
                            "birthDate" : "1999-03-24",
                            "about": "uni 3th grade",
                            "professions" : [
                                    "spring",
                                    "java",
                                    "sql"
                            ]
                        }
                        """);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(professionRepository.findAll()).isNotEmpty();
        assertThat(employeeRepository.findByEmployeeId("employee")).isPresent();

        cleanBeforeAndAfter("profession", "employee");

    }

    @WithMockUser(authorities = "STANDARD", value = "employee")
    @Test
    void should_get_applications() throws Exception {

        var request = get("/api/employee/applications")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                      "result":[
                      {
                      "entryTitle":"Looking for an IT guy",
                      "status":"WAITING"
                      }
                      ]
                        }
                        """));

    }

}