package com.sepehr.hotelbooking.dto.request;

import com.sepehr.hotelbooking.domain.RoomType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CreateRoomRequest {


    @NotBlank(message = "Room number is required.")
    @Size(
            max = 20,
            message = "Room number must not exceed 20 characters."
    )
    private String roomNumber;


    @NotNull(message = "Room type is required.")
    private RoomType roomType;


    @NotNull(message = "Price per night is required.")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price must be greater than zero."
    )
    private BigDecimal pricePerNight;


    @NotNull(message = "Hotel id is required.")
    private Long hotelId;


    public CreateRoomRequest(
            String roomNumber,
            RoomType roomType,
            BigDecimal pricePerNight,
            Long hotelId
    ) {

        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.hotelId = hotelId;
    }
}