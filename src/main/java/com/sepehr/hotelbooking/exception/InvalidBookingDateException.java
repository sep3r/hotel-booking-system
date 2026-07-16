package com.sepehr.hotelbooking.exception;


public class InvalidBookingDateException extends RuntimeException {


    public InvalidBookingDateException(String message) {

        super(message);
    }
}