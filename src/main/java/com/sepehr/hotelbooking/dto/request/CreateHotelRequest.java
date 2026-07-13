package com.sepehr.hotelbooking.dto.request;

import jakarta.validation.constraints.*;

public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required.")
    @Size(max = 100,
            message = "Hotel name must not exceed 100 characters.")
    private String hotelName;

    @NotBlank(message = "City is required.")
    @Size(
            max = 100,
            message = "City must not exceed 100 characters."
    )
    private String city;

    @NotBlank(message = "Address is required.")
    @Size(
            max = 300,
            message = "Address must not exceed 300 characters."
    )
    private String address;

    @NotNull(message = "Star rating is required.")
    @Min(value = 1, message = "Minimum star rating is 1.")
    @Max(value = 5, message = "Maximum star rating is 5.")
    private Integer starRating;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number must not exceed 20 characters.")
    private String phoneNumber;
}
