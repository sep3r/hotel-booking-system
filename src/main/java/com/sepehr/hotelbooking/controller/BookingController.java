package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.dto.request.CreateBookingRequest;
import com.sepehr.hotelbooking.dto.response.BookingResponse;
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
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody CreateBookingRequest request
    ) {

        BookingResponse response =
                bookingService.createBooking(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id
    ) {

        return ResponseEntity
                .ok(
                        bookingService.getBookingById(id)
                );
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {

        return ResponseEntity
                .ok(
                        bookingService.getAllBookings()
                );
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long id
    ) {

        bookingService.cancelBooking(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}