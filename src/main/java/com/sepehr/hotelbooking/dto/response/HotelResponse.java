package com.sepehr.hotelbooking.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class HotelResponse {


    private Long id;

    private String hotelName;

    private String city;

    private String address;

    private String description;

    private Integer starRating;

    private String phoneNumber;
}