package com.sepehr.hotelbooking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sepehr.hotelbooking.domain.Role;
import com.sepehr.hotelbooking.dto.request.CreateUserRequest;
import com.sepehr.hotelbooking.dto.response.UserResponse;
import com.sepehr.hotelbooking.security.CustomUserDetailsService;
import com.sepehr.hotelbooking.security.JwtService;
import com.sepehr.hotelbooking.service.UserService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @MockitoBean
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        UserResponse response =
                new UserResponse(
                        1L,
                        "Sepehr",
                        "Mirza",
                        "sepehr@test.com",
                        "+989121234567",
                        Role.CUSTOMER
                );

        when(userService.createUser(any(CreateUserRequest.class)))
                .thenReturn(response);

        CreateUserRequest request =
                new CreateUserRequest(
                        "Sepehr",
                        "Mirza",
                        "sepehr@test.com",
                        "password123",
                        "+989121234567"
                );

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.email")
                        .value("sepehr@test.com"));

    }

    @Test
    void shouldGetAllUsersSuccessfully() throws Exception {

        UserResponse response =
                new UserResponse(
                        1L,
                        "Sepehr",
                        "Mirza",
                        "sepehr@test.com",
                        "+989121234567",
                        Role.CUSTOMER
                );

        when(userService.getAllUsers())
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/users")
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()")
                        .value(1))

                .andExpect(jsonPath("$[0].email")
                        .value("sepehr@test.com"));

    }

    @Test
    void shouldReturnBadRequestWhenValidationFails()
            throws Exception {

        CreateUserRequest request =
                new CreateUserRequest(
                        "",
                        "",
                        "",
                        "",
                        ""
                );

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldReturnBadRequestWhenCreateUserRequestIsInvalid()
            throws Exception {


        CreateUserRequest request =
                new CreateUserRequest(
                        "",
                        "",
                        "invalid-email",
                        "123",
                        "abc"
                );


        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )

                .andExpect(status().isBadRequest());


        verify(
                userService,
                never()
        )
                .createUser(any(CreateUserRequest.class));

    }
}