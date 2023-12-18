package com.khagani.to_do_app.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khagani.to_do_app.dto.UserRequest;
import com.khagani.to_do_app.entity.Permission;
import com.khagani.to_do_app.entity.Role;
import com.khagani.to_do_app.entity.User;
import com.khagani.to_do_app.repository.UserRepository;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    private final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
            ".eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjo5OTk5OTk5OTk5LCJpYXQiOjE2OTQ3OTc5OTN9" +
            ".Ai46Wa6oTRUGLAje3hWXzVWJo5KLD9bQUOJ9LcGXUewSvBQIM4e6MgZw_bIrIkLcLAaqO3_lCmquH2IOfqe6wg";

    @BeforeEach
    void setUp() {
        User validUser = new User();
        validUser.setUsername("test@test.com");
        Role admin = new Role();
        Permission createUser = new Permission();
        createUser.setName("CREATE_USER");
        Permission readUser = new Permission();
        readUser.setName("READ_USER");
        admin.setPermission(List.of(createUser, readUser));
        validUser.setRoles(List.of(admin));
        when(userRepository.findByUsername("test@test.com")).thenReturn(Optional.of(validUser));
    }

    @Test
    void addUserAndExpect401() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Ali");
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userRequest).getBytes()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addUserAndExpect400() throws Exception {
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content("{\"name\": ali}".getBytes()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserAndExpectOk() throws Exception {

        User mockUser = new User();
        mockUser.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("aa@bb.cc");
        userRequest.setName("mock name");
        userRequest.setPassword("123456");

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(mapper.writeValueAsString(userRequest).getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserByIdAndExpectOk() throws Exception {
        Long id = 1L;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/v1/users/1")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
    }

    @Test
    void getUserByIdAndExpect404() throws Exception {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/users/1")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateUserByIdOk() throws Exception {
        Long id = 1L;

        User mockUser = new User();
        mockUser.setId(1L);

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("aa@bb.cc");
        userRequest.setName("mock name");
        userRequest.setPassword("123456");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(mapper.writeValueAsString(userRequest).getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserById404() throws Exception {
        Long id = 1L;

        User mockUser = new User();
        mockUser.setName("ali");
        when(userRepository.findById(id)).thenReturn(Optional.empty());


        mockMvc.perform(put("/v1/users/1")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserById() {
    }
}