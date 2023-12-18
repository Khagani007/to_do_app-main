package com.khagani.to_do_app.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khagani.to_do_app.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserControllerTestIntegration{

    private final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjo5OTk5OTk5OTk5LCJpYXQiOjE2OTQ3OTc5OTN9.Ai46Wa6oTRUGLAje3hWXzVWJo5KLD9bQUOJ9LcGXUewSvBQIM4e6MgZw_bIrIkLcLAaqO3_lCmquH2IOfqe6wg";
    @Container
    static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));


    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

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
    void addUserAndExpectOk() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("aa@bb.cc");
        userRequest.setName("mock name");
        userRequest.setPassword("123456");

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(mapper.writeValueAsString(userRequest).getBytes()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUserById() {
    }
}