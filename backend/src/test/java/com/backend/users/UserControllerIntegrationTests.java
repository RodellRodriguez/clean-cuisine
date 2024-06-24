package com.backend.users;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_ShouldCreateUser_WhenGivenUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User expectedUser = new User(1L, "Bob The Builder", "builder");
        
        mockMvc.perform(
            post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(expectedUser))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(expectedUser.getId()))
        .andExpect(jsonPath("$.name").value(expectedUser.getName()))
        .andExpect(jsonPath("$.role").value(expectedUser.getRole()));
    }

}