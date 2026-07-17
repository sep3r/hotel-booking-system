package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.domain.Payment;
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
    public ResponseEntity<Payment> createPayment(
            @PathVariable Long bookingId
    ) {


        Payment payment =
                paymentService.createPayment(bookingId);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(payment);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @PathVariable Long id
    ) {


        Payment payment =
                paymentService.getPaymentById(id);


        return ResponseEntity
                .ok(payment);
    }



    @PatchMapping("/{id}/success")
    public ResponseEntity<Void> processSuccessfulPayment(
            @PathVariable Long id,
            @RequestParam String transactionId
    ) {


        paymentService.processSuccessfulPayment(
                id,
                transactionId
        );


        return ResponseEntity
                .ok()
                .build();
    }



    @PatchMapping("/{id}/failed")
    public ResponseEntity<Void> processFailedPayment(
            @PathVariable Long id
    ) {


        paymentService.processFailedPayment(id);


        return ResponseEntity
                .ok()
                .build();
    }



    @PatchMapping("/{id}/refund")
    public ResponseEntity<Void> refundPayment(
            @PathVariable Long id
    ) {

        paymentService.refundPayment(id);


        return ResponseEntity
                .ok()
                .build();
    }
}