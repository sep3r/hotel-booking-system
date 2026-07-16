package com.sepehr.hotelbooking.service.impl;


import com.sepehr.hotelbooking.domain.Booking;
import com.sepehr.hotelbooking.domain.Room;
import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateBookingRequest;
import com.sepehr.hotelbooking.exception.BookingConflictException;
import com.sepehr.hotelbooking.exception.InvalidBookingDateException;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.BookingRepository;
import com.sepehr.hotelbooking.repository.RoomRepository;
import com.sepehr.hotelbooking.repository.UserRepository;
import com.sepehr.hotelbooking.service.BookingService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;


    @Override
    @Transactional
    public Booking createBooking(CreateBookingRequest request) {


        if (!request.getCheckOutDate()
                .isAfter(request.getCheckInDate())) {

            throw new InvalidBookingDateException(
                    "Check-out date must be after check-in date."
            );
        }


        User user = userRepository.findById(request.getUserId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()
                        )
                );


        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Room not found with id: "
                                        + request.getRoomId()
                        )
                );


        boolean alreadyBooked =
                bookingRepository
                        .existsByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                                request.getRoomId(),
                                request.getCheckOutDate(),
                                request.getCheckInDate()
                        );


        if (alreadyBooked) {

            throw new BookingConflictException(
                    "Room is already booked for this period."
            );
        }


        long nights = ChronoUnit.DAYS.between(
                request.getCheckInDate(),
                request.getCheckOutDate()
        );


        BigDecimal totalPrice =
                room.getPricePerNight()
                        .multiply(
                                BigDecimal.valueOf(nights)
                        );


        Booking booking = new Booking(
                user,
                room,
                request.getCheckInDate(),
                request.getCheckOutDate(),
                totalPrice
        );


        return bookingRepository.save(booking);
    }


    @Override
    public Booking getBookingById(Long id) {

        return bookingRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Booking not found with id: " + id
                        )
                );
    }


    @Override
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }


    @Override
    @Transactional
    public void cancelBooking(Long id) {

        Booking booking = getBookingById(id);

        booking.cancel();
    }
}