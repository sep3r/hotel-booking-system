package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.domain.Booking;
import com.sepehr.hotelbooking.dto.request.CreateBookingRequest;

import java.util.List;


public interface BookingService {


    Booking createBooking(CreateBookingRequest request);


    Booking getBookingById(Long id);


    List<Booking> getAllBookings();


    void cancelBooking(Long id);
}