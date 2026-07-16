package com.sepehr.hotelbooking.repository;


import com.sepehr.hotelbooking.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository
        extends JpaRepository<Payment, Long> {


    boolean existsByBookingId(Long bookingId);

}