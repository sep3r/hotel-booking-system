package com.sepehr.hotelbooking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sepehr.hotelbooking.dto.request.RegisterRequest;
import com.sepehr.hotelbooking.dto.response.AuthResponse;
import com.sepehr.hotelbooking.security.JwtAuthenticationFilter;
import com.sepehr.hotelbooking.service.AuthService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final ObjectMapper objectMapper =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule());

    @Test
    void shouldRegisterSuccessfully() throws Exception {

        AuthResponse response =
                new AuthResponse("jwt-token");
        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(response);
        RegisterRequest request =
                new RegisterRequest(
                        "Sepehr",
                        "Mirzanezhad",
                        "test@test.com",
                        "password123",
                        "+491234567890"
                );
        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token")
                        .value("jwt-token"));
    }
}