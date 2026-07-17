package com.sepehr.hotelbooking.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class CreateBookingRequest {


    @NotNull(message = "User id is required.")
    @Positive(message = "User id must be positive.")
    private Long userId;


    @NotNull(message = "Room id is required.")
    @Positive(message = "Room id must be positive.")
    private Long roomId;


    @NotNull(message = "Check-in date is required.")
    private LocalDate checkInDate;


    @NotNull(message = "Check-out date is required.")
    private LocalDate checkOutDate;
}