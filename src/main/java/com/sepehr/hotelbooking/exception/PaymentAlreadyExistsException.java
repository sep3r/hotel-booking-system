package com.sepehr.hotelbooking.exception;

public class PaymentAlreadyExistsException extends RuntimeException {

    public PaymentAlreadyExistsException(String message) {

        super(message);
    }
}
