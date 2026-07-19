package com.sepehr.hotelbooking.exception;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestExceptionController {

    @GetMapping("/not-found")
    public void notFound() {

        throw new ResourceNotFoundException(
                "Resource not found"
        );
    }

    @GetMapping("/conflict")
    public void conflict() {

        throw new BookingConflictException(
                "Booking conflict"
        );
    }

    @GetMapping("/invalid-date")
    public void invalidDate() {

        throw new InvalidBookingDateException(
                "Invalid booking date"
        );
    }

    @GetMapping("/payment-exists")
    public void paymentExists() {

        throw new PaymentAlreadyExistsException(
                "Payment already exists"
        );
    }
}