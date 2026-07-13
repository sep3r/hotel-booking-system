package com.sepehr.hotelbooking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
    @Size(max = 20, message = "Room number must not exceed 20 characters.")
    @Column(
            name = "room_number",
            nullable = false,
            length = 20
    )
    private String roomNumber;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "hotel_id",
            nullable = false
    )
    private Hotel hotel;


    protected Room() {
    }
}