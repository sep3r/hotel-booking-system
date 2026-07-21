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
            allocationSize = 50
    )
    private Long id;


    @NotBlank(message = "Hotel name is required.")
    @Size(max = 100)
    @Column(
            name = "hotel_name",
            nullable = false,
            length = 100
    )
    private String hotelName;


    @NotBlank(message = "City is required.")
    @Size(max = 100)
    @Column(
            nullable = false,
            length = 100
    )
    private String city;


    @NotBlank(message = "Address is required.")
    @Size(max = 300)
    @Column(
            nullable = false,
            length = 300
    )
    private String address;


    @Size(max = 500)
    @Column(length = 500)
    private String description;


    @Min(1)
    @Max(5)
    @Column(
            name = "star_rating",
            nullable = false
    )
    private Integer starRating;


    @NotBlank(message = "Phone number is required.")
    @Size(max = 20)
    @Column(
            name = "phone_number",
            nullable = false,
            length = 20
    )
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "manager_id",
            nullable = false
    )
    private User manager;

    public Hotel(
            String hotelName,
            String city,
            String address,
            String description,
            Integer starRating,
            String phoneNumber,
            User manager
    ) {

        this.hotelName = hotelName;
        this.city = city;
        this.address = address;
        this.description = description;
        this.starRating = starRating;
        this.phoneNumber = phoneNumber;
        this.manager = manager;
    }
}