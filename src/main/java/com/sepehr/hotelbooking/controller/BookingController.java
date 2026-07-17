package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.domain.Booking;
import com.sepehr.hotelbooking.dto.request.CreateBookingRequest;
import com.sepehr.hotelbooking.service.BookingService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;


    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @Valid @RequestBody CreateBookingRequest request
    ) {


        Booking booking = bookingService.createBooking(request);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(
            @PathVariable Long id
    ) {


        Booking booking = bookingService.getBookingById(id);


        return ResponseEntity
                .ok(booking);
    }


    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {


        return ResponseEntity
                .ok(
                        bookingService.getAllBookings()
                );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long id
    ) {


        bookingService.cancelBooking(id);


        return ResponseEntity
                .noContent()
                .build();
    }
}