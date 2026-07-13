package com.sepehr.hotelbooking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "hotel")
public class Hotel {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_seq_generator"
    )
    @SequenceGenerator(
            name = "hotel_seq_generator",
            sequenceName = "hotel_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private Long id;


    @NotBlank(message = "Hotel name is required.")
    @Size(
            max = 100,
            message = "Hotel name must not exceed 100 characters."
    )
    @Column(
            name = "hotel_name",
            nullable = false,
            length = 100
    )
    private String hotelName;


    @NotBlank(message = "City is required.")
    @Size(
            max = 100,
            message = "City must not exceed 100 characters."
    )
    @Column(
            nullable = false,
            length = 100
    )
    private String city;


    @NotBlank(message = "Address is required.")
    @Size(
            max = 300,
            message = "Address must not exceed 300 characters."
    )
    @Column(
            nullable = false,
            length = 300
    )
    private String address;


    @NotNull(message = "Star rating is required.")
    @Min(
            value = 1,
            message = "Minimum star rating is 1."
    )
    @Max(
            value = 5,
            message = "Maximum star rating is 5."
    )
    @Column(
            name = "star_rating",
            nullable = false
    )
    private Integer starRating;


    @NotBlank(message = "Phone number is required.")
    @Size(
            max = 20,
            message = "Phone number must not exceed 20 characters."
    )
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Invalid phone number format."
    )
    @Column(
            name = "phone_number",
            nullable = false,
            length = 20
    )
    private String phoneNumber;


    public Hotel(
            String hotelName,
            String city,
            String address,
            Integer starRating,
            String phoneNumber
    ) {
        this.hotelName = hotelName;
        this.city = city;
        this.address = address;
        this.starRating = starRating;
        this.phoneNumber = phoneNumber;
    }
}