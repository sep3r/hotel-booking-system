package com.sepehr.hotelbooking.service;


import com.sepehr.hotelbooking.domain.Payment;


public interface PaymentService {


    Payment createPayment(Long bookingId);


    Payment getPaymentById(Long id);


    void processSuccessfulPayment(
            Long paymentId,
            String transactionId
    );


    void processFailedPayment(Long paymentId);


    void refundPayment(Long paymentId);

}