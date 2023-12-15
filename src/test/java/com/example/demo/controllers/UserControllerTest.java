package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.model.InternalUserDTO;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    MockMvc mockMvc;

    @Value("${resource-server.url}")
    String resourceServerUrl;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @Test
    void getUserDetailsCorrectRequestTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get(resourceServerUrl + "/mojombo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        InternalUserDTO userDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), InternalUserDTO.class);

        Mockito.verify(userRepository).save(Mockito.any(User.class));

        assertEquals("User", userDTO.getType());
        assertNotEquals(0.0, userDTO.getCalculations());
    }

    @Test
    void getUserDetailsNotExistingUserLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get(resourceServerUrl + "/pietruszka12345")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn();

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));

        assertTrue(mvcResult.getResponse().getContentAsString().contains("404 Not Found - User with this login does not exist"));
    }
}