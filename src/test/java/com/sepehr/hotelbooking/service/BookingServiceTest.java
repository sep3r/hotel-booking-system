package com.sepehr.hotelbooking.service;


import com.sepehr.hotelbooking.domain.Booking;
import com.sepehr.hotelbooking.domain.BookingStatus;
import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.domain.Room;
import com.sepehr.hotelbooking.domain.RoomType;
import com.sepehr.hotelbooking.domain.User;

import com.sepehr.hotelbooking.dto.request.CreateBookingRequest;
import com.sepehr.hotelbooking.dto.response.BookingResponse;

import com.sepehr.hotelbooking.exception.BookingConflictException;
import com.sepehr.hotelbooking.exception.InvalidBookingDateException;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;

import com.sepehr.hotelbooking.repository.BookingRepository;
import com.sepehr.hotelbooking.repository.RoomRepository;
import com.sepehr.hotelbooking.repository.UserRepository;

import com.sepehr.hotelbooking.service.impl.BookingServiceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;



class BookingServiceTest {


    @Mock
    private BookingRepository bookingRepository;


    @Mock
    private UserRepository userRepository;


    @Mock
    private RoomRepository roomRepository;



    @InjectMocks
    private BookingServiceImpl bookingService;



    private User user;

    private Hotel hotel;

    private Room room;



    @BeforeEach
    void setup() {


        MockitoAnnotations.openMocks(this);



        user = new User(
                "Sepehr",
                "Mirza",
                "sepehr@test.com",
                "password123",
                "+989121234567"
        );



        hotel = new Hotel(
                "Hilton",
                "Paris",
                "France",
                "Luxury hotel",
                5,
                "+33123456789"
        );



        room = new Room(
                "101",
                RoomType.DOUBLE,
                BigDecimal.valueOf(100),
                hotel
        );

    }





    @Test
    void shouldCreateBookingSuccessfully() {


        CreateBookingRequest request =
                new CreateBookingRequest(
                        1L,
                        1L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 4)
                );



        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));



        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));



        when(
                bookingRepository
                        .existsByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                                1L,
                                request.getCheckOutDate(),
                                request.getCheckInDate()
                        )
        )
                .thenReturn(false);



        Booking booking = new Booking(
                user,
                room,
                request.getCheckInDate(),
                request.getCheckOutDate(),
                BigDecimal.valueOf(300)
        );



        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking);



        BookingResponse response =
                bookingService.createBooking(request);



        assertThat(response)
                .isNotNull();



        assertThat(response.getTotalPrice())
                .isEqualTo(BigDecimal.valueOf(300));



        verify(userRepository)
                .findById(1L);



        verify(roomRepository)
                .findById(1L);



        verify(bookingRepository)
                .save(any(Booking.class));

    }







    @Test
    void shouldThrowExceptionWhenCheckoutDateBeforeCheckinDate() {


        CreateBookingRequest request =
                new CreateBookingRequest(
                        1L,
                        1L,
                        LocalDate.of(2026, 8, 10),
                        LocalDate.of(2026, 8, 5)
                );



        assertThatThrownBy(
                () -> bookingService.createBooking(request)
        )
                .isInstanceOf(InvalidBookingDateException.class);



        verifyNoInteractions(
                userRepository,
                roomRepository,
                bookingRepository
        );

    }







    @Test
    void shouldThrowExceptionWhenRoomAlreadyBooked() {


        CreateBookingRequest request =
                new CreateBookingRequest(
                        1L,
                        1L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 4)
                );



        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));



        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));



        when(
                bookingRepository
                        .existsByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                                1L,
                                request.getCheckOutDate(),
                                request.getCheckInDate()
                        )
        )
                .thenReturn(true);



        assertThatThrownBy(
                () -> bookingService.createBooking(request)
        )
                .isInstanceOf(BookingConflictException.class);



        verify(bookingRepository, never())
                .save(any());

    }







    @Test
    void shouldThrowExceptionWhenUserNotFound() {


        CreateBookingRequest request =
                new CreateBookingRequest(
                        99L,
                        1L,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 4)
                );



        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());



        assertThatThrownBy(
                () -> bookingService.createBooking(request)
        )
                .isInstanceOf(ResourceNotFoundException.class);



        verify(roomRepository, never())
                .findById(any());

    }







    @Test
    void shouldCancelBookingSuccessfully() {


        Booking booking =
                new Booking(
                        user,
                        room,
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 4),
                        BigDecimal.valueOf(300)
                );



        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));



        bookingService.cancelBooking(1L);



        assertThat(booking.getStatus())
                .isEqualTo(BookingStatus.CANCELLED);



        verify(bookingRepository)
                .findById(1L);

    }

}