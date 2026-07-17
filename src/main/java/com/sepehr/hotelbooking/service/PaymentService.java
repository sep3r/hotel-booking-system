package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.dto.response.PaymentResponse;


public interface PaymentService {

    PaymentResponse createPayment(Long bookingId);

    PaymentResponse getPaymentById(Long id);

    void processSuccessfulPayment(Long paymentId, String transactionId);

    void processFailedPayment(Long paymentId);

    void refundPayment(Long paymentId);
}