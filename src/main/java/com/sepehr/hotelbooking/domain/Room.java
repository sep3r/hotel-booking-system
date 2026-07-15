package com.sepehr.hotelbooking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(
        name = "room",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_room_number_hotel",
                        columnNames = {
                                "room_number",
                                "hotel_id"
                        }
                )
        }
)
public class Room {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_seq_generator"
    )
    @SequenceGenerator(
            name = "room_seq_generator",
            sequenceName = "room_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private Long id;


    @NotBlank(message = "Room number is required.")
    @Size(
            max = 20,
            message = "Room number must not exceed 20 characters."
    )
    @Column(
            name = "room_number",
            nullable = false,
            length = 20
    )
    private String roomNumber;


    @Enumerated(EnumType.STRING)
    @Column(
            name = "room_type",
            nullable = false,
            length = 30
    )
    private RoomType roomType;


    @NotNull(message = "Price per night is required.")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price must be greater than zero."
    )
    @Column(
            name = "price_per_night",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal pricePerNight;


    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private RoomStatus status;


    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "hotel_id",
            nullable = false
    )
    private Hotel hotel;


    public Room(
            String roomNumber,
            RoomType roomType,
            BigDecimal pricePerNight,
            Hotel hotel
    ) {

        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.status = RoomStatus.AVAILABLE;
        this.hotel = hotel;
    }


    public void markAsMaintenance() {

        this.status = RoomStatus.MAINTENANCE;
    }


    public void makeAvailable() {

        this.status = RoomStatus.AVAILABLE;
    }


    public void markAsOutOfService() {

        this.status = RoomStatus.OUT_OF_SERVICE;
    }
}