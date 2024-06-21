package com.backend.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UserController.class) // See https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.spring-mvc-tests
public class UserControllerTests {
    
    @MockBean
    UserService mockUserService;

    @Autowired
	private MockMvc mockMvc;

    @Test
    public void create_ShouldCreateUser_WhenGivenUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User expectedUser = new User(1L, "Bob The Builder", "builder");
        when(mockUserService.save(any(User.class))).thenReturn(expectedUser);  // Do NOT write save(expectedUser) because mockUserService.save will only accept the exact expectedUser instance as input!
        
        mockMvc.perform(
            post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(expectedUser))
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(expectedUser.getId()))
        .andExpect(jsonPath("$.name").value(expectedUser.getName()))
        .andExpect(jsonPath("$.role").value(expectedUser.getRole()));
    }

    @Test
    public void findById_ShouldReturnUser_WhenGivenExistingId() throws Exception {
        Long existingId = 1L;
        User expectedUser = new User(existingId, "Bob The Builder", "builder");
        when(mockUserService.findById(existingId)).thenReturn(Optional.of(expectedUser));

        mockMvc.perform(
            get(String.format("/users/%s", existingId))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(jsonPath("$.id").value(expectedUser.getId()))
        .andExpect(jsonPath("$.name").value(expectedUser.getName()))
        .andExpect(jsonPath("$.role").value(expectedUser.getRole()));
    }

    @Test
    public void findById_ShouldReturnNotFound_WhenGivenNonExistingId() throws Exception {
        Long nonExistingId = 1L;
        when(mockUserService.findById(nonExistingId)).thenReturn(Optional.empty());

        mockMvc.perform(
            get(String.format("/users/%s", nonExistingId))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andDo(print());
    }

}
