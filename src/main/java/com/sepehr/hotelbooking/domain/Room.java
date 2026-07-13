package com.sepehr.hotelbooking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_seq_generator"
    )
    @SequenceGenerator(
            name = "room_seq_generator",
            sequenceName = "room_seq",
            allocationSize = 50
    )
    private Long id;

    @NotBlank(message = "Room number is required.")
    @Column(nullable = false)
    private String roomNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "hotel_id",
            nullable = false
    )
    private Hotel hotel;

    protected Room() {
    }

    public Room(String roomNumber, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Hotel getHotel() {
        return hotel;
    }
}