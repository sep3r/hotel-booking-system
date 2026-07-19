package com.sepehr.hotelbooking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.sepehr.hotelbooking.domain.BookingStatus;
import com.sepehr.hotelbooking.dto.request.CreateBookingRequest;
import com.sepehr.hotelbooking.dto.response.BookingResponse;
import com.sepehr.hotelbooking.service.BookingService;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookingController.class)
class BookingControllerTest {


    @Autowired
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule());


    @MockitoBean
    private BookingService bookingService;


    @Test
    void shouldCreateBookingSuccessfully() throws Exception {


        BookingResponse response =
                new BookingResponse(
                        1L,
                        10L,
                        20L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5),
                        BigDecimal.valueOf(600),
                        BookingStatus.CONFIRMED
                );


        when(bookingService.createBooking(any(CreateBookingRequest.class)))
                .thenReturn(response);


        CreateBookingRequest request =
                new CreateBookingRequest(
                        10L,
                        20L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5)
                );


        mockMvc.perform(
                        post("/api/bookings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.userId")
                        .value(10))

                .andExpect(jsonPath("$.roomId")
                        .value(20));

    }


    @Test
    void shouldGetBookingByIdSuccessfully() throws Exception {


        BookingResponse response =
                new BookingResponse(
                        1L,
                        10L,
                        20L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5),
                        BigDecimal.valueOf(600),
                        BookingStatus.CONFIRMED
                );


        when(bookingService.getBookingById(1L))
                .thenReturn(response);


        mockMvc.perform(
                        get("/api/bookings/1")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.userId")
                        .value(10))

                .andExpect(jsonPath("$.roomId")
                        .value(20));

    }

    @Test
    void shouldGetAllBookingsSuccessfully() throws Exception {

        BookingResponse response =
                new BookingResponse(
                        1L,
                        10L,
                        20L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 5),
                        BigDecimal.valueOf(600),
                        BookingStatus.CONFIRMED
                );


        when(bookingService.getAllBookings())
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/bookings")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()")
                        .value(1))

                .andExpect(jsonPath("$[0].roomId")
                        .value(20));

    }

    @Test
    void shouldCancelBookingSuccessfully() throws Exception {

        mockMvc.perform(
                        put("/api/bookings/1/cancel")
                )

                .andExpect(status().isNoContent());

        verify(bookingService)
                .cancelBooking(1L);

    }
}