package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface BookingRepository
        extends JpaRepository<Booking, Long> {


    boolean existsByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            Long roomId,
            LocalDate checkOutDate,
            LocalDate checkInDate
    );

}