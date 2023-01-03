package com.kodizim.findaduck.api.user;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.application.TestDataService;
import com.kodizim.findaduck.application.user.CompanyService;
import com.kodizim.findaduck.domain.employee.EmployeeRepository;
import com.kodizim.findaduck.domain.employee.ProfessionRepository;
import com.kodizim.findaduck.domain.job.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EmployeeControllerTest extends BaseTestClass {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProfessionRepository professionRepository;
    @Autowired
    TestDataService testDataService;
    @Autowired
    CompanyService companyService;
    @Autowired
    JobRepository jobRepository;



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
                        {"result":[
                            {
                            "applicationId":"%s",
                            "entryTitle":"Looking for an IT guy",
                            "employeeName":"murat",
                            "employeeSurname":"yıldırım",
                            "appliedOn":"2021-04-01T19:30:00+03:00",
                            "status":"WAITING"
                            }
                            ]
                        }
                        """.formatted(application.getApplicationId())));

    }
    @WithMockUser(authorities = "STANDARD", value = "employee")
    @Test
    void should_get_jobs() throws Exception {
        companyService.acceptApplication(application.getApplicationId(),"company");

        var jobDto = jobRepository.getJobDtoByEmployeeId("employee")
                .get(0);

        mockMvc.perform(get("/api/employee/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "result":[
                        {
                        "jobId": "%s",
                        "companyName":"testCompany",
                        "employeeId":"employee",
                        
                        "title":"Looking for an IT guy",
                        "content":"IT guy",
                        "rating":null}]

                        }
                        """.formatted(jobDto.getJobId())));
    }

    @WithMockUser(authorities = "STANDARD", value = "employee1")
    @Test
    void should_update_employee() throws Exception {
        testDataService.addEmployee("employee1");
        var request = put("/api/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "phoneNumber" : "5116956",
                        "photoLocationKey": "somepublicUrl.com",
                        "birthDate": "2005-06-28",
                        "about": "about has changed",
                        "professions": ["changed profession 1","changed profession 2"]
                        }
                        """);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        var employee = employeeRepository.findByEmployeeId("employee1").orElseThrow();
        assertThat(employee.getAbout()).isEqualTo("about has changed");
        assertThat(employee.getBirthDate()).isEqualTo("2005-06-28");
        assertThat(employee.getPhotoLocationKey()).isEqualTo("somepublicUrl.com");

    }

}