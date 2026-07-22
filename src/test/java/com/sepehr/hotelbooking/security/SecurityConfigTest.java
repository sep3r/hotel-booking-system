package com.sepehr.hotelbooking.security;


import com.sepehr.hotelbooking.controller.BookingController;
import com.sepehr.hotelbooking.service.BookingService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private BookingService bookingService;


    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void shouldRejectRequestWithoutAuthentication()
            throws Exception {


        mockMvc.perform(
                        get("/api/bookings")
                )
                .andExpect(
                        status().isUnauthorized()
                );
    }
}