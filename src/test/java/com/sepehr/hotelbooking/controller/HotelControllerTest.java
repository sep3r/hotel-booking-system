package com.sepehr.hotelbooking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.dto.response.HotelResponse;
import com.sepehr.hotelbooking.service.HotelService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.never;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @MockitoBean
    private HotelService hotelService;

    @Test
    void shouldCreateHotelSuccessfully() throws Exception {

        HotelResponse response =
                new HotelResponse(
                        1L,
                        "Hilton",
                        "Paris",
                        "Champs Elysees",
                        "Luxury hotel",
                        5,
                        "+33123456789"
                );

        when(hotelService.createHotel(any(CreateHotelRequest.class)))
                .thenReturn(response);

        CreateHotelRequest request =
                new CreateHotelRequest(
                        "Hilton",
                        "Paris",
                        "Champs Elysees",
                        5,
                        "+33123456789",
                        "Luxury hotel"
                );

        mockMvc.perform(
                        post("/api/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.hotelName")
                        .value("Hilton"));

    }

    @Test
    void shouldGetHotelByIdSuccessfully() throws Exception {

        HotelResponse response =
                new HotelResponse(
                        1L,
                        "Hilton",
                        "Paris",
                        "Champs Elysees",
                        "Luxury hotel",
                        5,
                        "+33123456789"
                );

        when(hotelService.getHotelById(1L))
                .thenReturn(response);


        mockMvc.perform(
                        get("/api/hotels/1")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.hotelName")
                        .value("Hilton"))

                .andExpect(jsonPath("$.city")
                        .value("Paris"));

    }

    @Test
    void shouldGetAllHotelsSuccessfully() throws Exception {

        HotelResponse response =
                new HotelResponse(
                        1L,
                        "Hilton",
                        "Paris",
                        "Champs Elysees",
                        "Luxury hotel",
                        5,
                        "+33123456789"
                );

        when(hotelService.getAllHotels())
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/hotels")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()")
                        .value(1))

                .andExpect(jsonPath("$[0].hotelName")
                        .value("Hilton"));

    }

    @Test
    void shouldDeleteHotelSuccessfully() throws Exception {

        mockMvc.perform(
                        delete("/api/hotels/1")
                )

                .andExpect(status().isNoContent());

        verify(hotelService)
                .deleteHotel(1L);

    }

    @Test
    void shouldReturnBadRequestWhenCreateHotelRequestIsInvalid()
            throws Exception {

        CreateHotelRequest request =
                new CreateHotelRequest(
                        "",
                        "",
                        "",
                        0,
                        "",
                        "Description"
                );

        mockMvc.perform(
                        post("/api/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )

                .andExpect(status().isBadRequest());
        verify(
                hotelService,
                never()
        )
                .createHotel(any(CreateHotelRequest.class));
    }
}