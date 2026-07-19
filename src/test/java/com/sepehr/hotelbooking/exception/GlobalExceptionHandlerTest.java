package com.sepehr.hotelbooking.exception;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {


    @Autowired
    private MockMvc mockMvc;



    @Test
    void shouldReturn404WhenResourceNotFoundExceptionThrown()
            throws Exception {


        mockMvc.perform(
                        get("/test/not-found")
                )

                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$.message")
                        .value("Resource not found"))

                .andExpect(jsonPath("$.timestamp")
                        .exists());

    }




    @Test
    void shouldReturn409WhenBookingConflictExceptionThrown()
            throws Exception {


        mockMvc.perform(
                        get("/test/conflict")
                )

                .andExpect(status().isConflict())

                .andExpect(jsonPath("$.message")
                        .value("Booking conflict"));

    }




    @Test
    void shouldReturn400WhenInvalidBookingDateExceptionThrown()
            throws Exception {


        mockMvc.perform(
                        get("/test/invalid-date")
                )

                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.message")
                        .value("Invalid booking date"));

    }




    @Test
    void shouldReturn409WhenPaymentAlreadyExistsExceptionThrown()
            throws Exception {


        mockMvc.perform(
                        get("/test/payment-exists")
                )

                .andExpect(status().isConflict())

                .andExpect(jsonPath("$.message")
                        .value("Payment already exists"));

    }

}