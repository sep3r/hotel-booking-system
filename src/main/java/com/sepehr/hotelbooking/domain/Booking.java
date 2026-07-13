package com.sepehr.hotelbooking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_seq_generator"
    )
    @SequenceGenerator(
            name = "booking_seq_generator",
            sequenceName = "booking_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "room_id",
            nullable = false
    )
    private Room room;

    @NotNull(message = "Check-in date is required.")
    @Column(
            name = "check_in_date",
            nullable = false
    )
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required.")
    @Column(
            name = "check_out_date",
            nullable = false
    )
    private LocalDate checkOutDate;

    @NotNull(message = "Total price is required.")
    @Column(
            name = "total_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private BookingStatus status;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    public Booking(
            User user,
            Room room,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            BigDecimal totalPrice
    ) {
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.status = BookingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
}