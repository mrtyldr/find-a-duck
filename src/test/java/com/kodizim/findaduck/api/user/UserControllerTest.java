package com.kodizim.findaduck.api.user;

import com.kodizim.findaduck.BaseTestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseTestClass {
    @Autowired
    MockMvc mockMvc;

    @WithMockUser(authorities = "STANDARD", value = "company")
    @Test
    void should_get_userInfo() throws Exception {
        mockMvc.perform(get("/api/user-info"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "result":{
                        "userId":"company",
                        "type":"COMPANY",
                        "ondboardingDone":true
                        }
                        }
                        """,true));
    }
}