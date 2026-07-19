package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.domain.PaymentStatus;
import com.sepehr.hotelbooking.dto.response.PaymentResponse;
import com.sepehr.hotelbooking.security.CustomUserDetailsService;
import com.sepehr.hotelbooking.security.JwtService;
import com.sepehr.hotelbooking.service.PaymentService;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.math.BigDecimal;
import java.time.LocalDateTime;



import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void shouldCreatePaymentSuccessfully() throws Exception {

        PaymentResponse response =
                new PaymentResponse(
                        1L,
                        10L,
                        BigDecimal.valueOf(500),
                        PaymentStatus.PENDING,
                        null,
                        null
                );
        when(paymentService.createPayment(10L))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/payments/booking/10")
                )

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.bookingId")
                        .value(10))

                .andExpect(jsonPath("$.status")
                        .value("PENDING"));

    }

    @Test
    void shouldGetPaymentByIdSuccessfully() throws Exception {

        PaymentResponse response =
                new PaymentResponse(
                        1L,
                        10L,
                        BigDecimal.valueOf(500),
                        PaymentStatus.SUCCESS,
                        "TX123",
                        LocalDateTime.of(
                                2026,
                                8,
                                1,
                                12,
                                0
                        )
                );

        when(paymentService.getPaymentById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/payments/1")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.bookingId")
                        .value(10))

                .andExpect(jsonPath("$.transactionId")
                        .value("TX123"));

    }

    @Test
    void shouldProcessSuccessfulPayment() throws Exception {

        mockMvc.perform(
                        put("/api/payments/1/success")
                                .param(
                                        "transactionId",
                                        "TX123"
                                )
                )

                .andExpect(status().isNoContent());

        verify(paymentService)
                .processSuccessfulPayment(
                        1L,
                        "TX123"
                );

    }

    @Test
    void shouldProcessFailedPayment() throws Exception {

        mockMvc.perform(
                        put("/api/payments/1/failed")
                )

                .andExpect(status().isNoContent());

        verify(paymentService)
                .processFailedPayment(1L);

    }

    @Test
    void shouldRefundPayment() throws Exception {

        mockMvc.perform(
                        put("/api/payments/1/refund")
                )

                .andExpect(status().isNoContent());
        verify(paymentService)
                .refundPayment(1L);

    }
}