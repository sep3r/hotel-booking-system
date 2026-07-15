package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {


}
