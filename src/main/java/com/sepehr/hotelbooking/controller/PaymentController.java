package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.dto.response.PaymentResponse;
import com.sepehr.hotelbooking.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponse> createPayment(
            @PathVariable Long bookingId
    ) {
        PaymentResponse response =
                paymentService.createPayment(bookingId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(
                        paymentService.getPaymentById(id)
                );
    }


    @PutMapping("/{id}/success")
    public ResponseEntity<Void> successfulPayment(
            @PathVariable Long id,
            @RequestParam String transactionId
    ) {
        paymentService.processSuccessfulPayment(
                id,
                transactionId
        );
        return ResponseEntity
                .noContent()
                .build();
    }


    @PutMapping("/{id}/failed")
    public ResponseEntity<Void> failedPayment(
            @PathVariable Long id
    ) {
        paymentService.processFailedPayment(id);
        return ResponseEntity
                .noContent()
                .build();
    }


    @PutMapping("/{id}/refund")
    public ResponseEntity<Void> refundPayment(
            @PathVariable Long id
    ) {
        paymentService.refundPayment(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}