package com.sepehr.hotelbooking.dto.response;

import com.sepehr.hotelbooking.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    private Long bookingId;

    private BigDecimal amount;

    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime paidAt;
}