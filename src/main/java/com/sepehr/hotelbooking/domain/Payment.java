package com.sepehr.hotelbooking.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "payment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_payment_transaction_id",
                        columnNames = "transaction_id"
                )
        }
)
public class Payment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_seq_generator"
    )
    @SequenceGenerator(
            name = "payment_seq_generator",
            sequenceName = "payment_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            unique = true
    )
    private Booking booking;

    @NotNull(message = "Payment amount is required.")
    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private PaymentStatus status;

    @Column(
            name = "transaction_id",
            length = 100
    )
    private String transactionId;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "paid_at"
    )
    private LocalDateTime paidAt;

    public Payment(
            Booking booking,
            BigDecimal amount
    )

    {
        this.booking = booking;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void markAsSuccessful(String transactionId) {

        this.status = PaymentStatus.SUCCESS;
        this.transactionId = transactionId;
        this.paidAt = LocalDateTime.now();
    }

    public void markAsFailed() {

        this.status = PaymentStatus.FAILED;
    }

    public void refund() {

        this.status = PaymentStatus.REFUNDED;
    }
}