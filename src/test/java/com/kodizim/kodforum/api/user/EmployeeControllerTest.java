package com.kodizim.kodforum.api.user;

import com.kodizim.kodforum.BaseTestClass;
import com.kodizim.kodforum.domain.employee.EmployeeRepository;
import com.kodizim.kodforum.domain.employee.ProfessionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class EmployeeControllerTest extends BaseTestClass {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProfessionRepository professionRepository;


    @WithMockUser(authorities = "STANDARD",value = "employee")
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

        cleanBeforeAndAfter("profession","employee");

    }

}