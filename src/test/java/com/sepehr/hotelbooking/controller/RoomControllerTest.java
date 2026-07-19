package com.sepehr.hotelbooking.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sepehr.hotelbooking.domain.RoomStatus;
import com.sepehr.hotelbooking.domain.RoomType;
import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
import com.sepehr.hotelbooking.dto.response.RoomResponse;
import com.sepehr.hotelbooking.service.RoomService;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.math.BigDecimal;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(RoomController.class)
class RoomControllerTest {



    @Autowired
    private MockMvc mockMvc;



    private final ObjectMapper objectMapper = new ObjectMapper();



    @MockitoBean
    private RoomService roomService;





    @Test
    void shouldCreateRoomSuccessfully() throws Exception {

        RoomResponse response =
                new RoomResponse(
                        1L,
                        "101",
                        RoomType.DOUBLE,
                        BigDecimal.valueOf(150),
                        RoomStatus.AVAILABLE,
                        1L
                );

        when(roomService.createRoom(any(CreateRoomRequest.class)))
                .thenReturn(response);

        CreateRoomRequest request =
                new CreateRoomRequest(
                        "101",
                        RoomType.DOUBLE,
                        BigDecimal.valueOf(150),
                        1L
                );

        mockMvc.perform(
                        post("/api/rooms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.roomNumber")
                        .value("101"))

                .andExpect(jsonPath("$.hotelId")
                        .value(1));

    }

    @Test
    void shouldGetRoomByIdSuccessfully() throws Exception {

        RoomResponse response =
                new RoomResponse(
                        1L,
                        "101",
                        RoomType.DOUBLE,
                        BigDecimal.valueOf(150),
                        RoomStatus.AVAILABLE,
                        1L
                );

        when(roomService.getRoomById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/rooms/1")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.roomNumber")
                        .value("101"))

                .andExpect(jsonPath("$.hotelId")
                        .value(1));

    }

    @Test
    void shouldGetAllRoomsSuccessfully() throws Exception {

        RoomResponse response =
                new RoomResponse(
                        1L,
                        "101",
                        RoomType.DOUBLE,
                        BigDecimal.valueOf(150),
                        RoomStatus.AVAILABLE,
                        1L
                );

        when(roomService.getAllRooms())
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/rooms")
                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()")
                        .value(1))

                .andExpect(jsonPath("$[0].roomNumber")
                        .value("101"));

    }

    @Test
    void shouldDeleteRoomSuccessfully() throws Exception {

        mockMvc.perform(
                        delete("/api/rooms/1")
                )

                .andExpect(status().isNoContent());

        verify(roomService)
                .deleteRoom(1L);

    }
}