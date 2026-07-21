package com.sepehr.hotelbooking.service;


import com.sepehr.hotelbooking.domain.*;
import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
import com.sepehr.hotelbooking.dto.response.RoomResponse;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.HotelRepository;
import com.sepehr.hotelbooking.repository.RoomRepository;
import com.sepehr.hotelbooking.service.impl.RoomServiceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Hotel hotel;

    private Room room;


    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        User manager = new User(
                "Sepehr",
                "Mirza",
                "manager@test.com",
                "password",
                "09123456789"
        );

        manager.changeRole(Role.HOTEL_MANAGER);

        hotel = new Hotel(
                "Hilton",
                "Berlin",
                "Address",
                "Description",
                5,
                "123456",
                manager
        );

        room = new Room(
                "101",
                RoomType.DOUBLE,
                BigDecimal.valueOf(150),
                hotel
        );
    }

    @Test
    void shouldCreateRoomSuccessfully() {

        CreateRoomRequest request =
                new CreateRoomRequest(
                        "101",
                        RoomType.DOUBLE,
                        BigDecimal.valueOf(150),
                        1L
                );

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));
        when(roomRepository.save(any(Room.class)))
                .thenReturn(room);
        RoomResponse response =
                roomService.createRoom(request);
        assertThat(response)
                .isNotNull();
        assertThat(response.getRoomNumber())
                .isEqualTo("101");
        assertThat(response.getPricePerNight())
                .isEqualTo(BigDecimal.valueOf(150));
        verify(hotelRepository)
                .findById(1L);
        verify(roomRepository)
                .save(any(Room.class));
    }


    @Test
    void shouldThrowExceptionWhenHotelNotFound() {

        CreateRoomRequest request =
                new CreateRoomRequest(
                        "101",
                        RoomType.DOUBLE,
                        BigDecimal.valueOf(150),
                        99L
                );
        when(hotelRepository.findById(99L))
                .thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> roomService.createRoom(request)
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Hotel not found with id: 99"
                );
        verify(roomRepository, never())
                .save(any(Room.class));

    }

    @Test
    void shouldGetRoomByIdSuccessfully() {
        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));
        RoomResponse response =
                roomService.getRoomById(1L);
        assertThat(response)
                .isNotNull();
        assertThat(response.getRoomNumber())
                .isEqualTo("101");
        verify(roomRepository)
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenRoomNotFound() {
        when(roomRepository.findById(10L))
                .thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> roomService.getRoomById(10L)
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Room not found with id: 10"
                );
    }

    @Test
    void shouldReturnAllRooms() {
        when(roomRepository.findAll())
                .thenReturn(
                        List.of(room)
                );
        List<RoomResponse> response =
                roomService.getAllRooms();
        assertThat(response)
                .hasSize(1);
        assertThat(response.get(0).getRoomNumber())
                .isEqualTo("101");
        verify(roomRepository)
                .findAll();
    }

    @Test
    void shouldDeleteRoomSuccessfully() {
        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));
        roomService.deleteRoom(1L);
        verify(roomRepository)
                .delete(room);
    }
}