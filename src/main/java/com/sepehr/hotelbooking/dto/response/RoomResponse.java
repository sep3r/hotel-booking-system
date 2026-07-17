package com.sepehr.hotelbooking.dto.response;

import com.sepehr.hotelbooking.domain.RoomStatus;
import com.sepehr.hotelbooking.domain.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RoomResponse {

    private Long id;

    private String roomNumber;

    private RoomType roomType;

    private BigDecimal pricePerNight;

    private RoomStatus status;

    private Long hotelId;
}