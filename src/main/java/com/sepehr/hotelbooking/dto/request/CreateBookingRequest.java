package com.sepehr.hotelbooking.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class CreateBookingRequest {


    @NotNull(message = "User id is required.")
    private Long userId;


    @NotNull(message = "Room id is required.")
    private Long roomId;


    @NotNull(message = "Check-in date is required.")
    private LocalDate checkInDate;


    @NotNull(message = "Check-out date is required.")
    private LocalDate checkOutDate;



    public Long getUserId() {
        return userId;
    }


    public Long getRoomId() {
        return roomId;
    }


    public LocalDate getCheckInDate() {
        return checkInDate;
    }


    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}