package com.sepehr.hotelbooking.service.impl;

import com.sepehr.hotelbooking.domain.Booking;
import com.sepehr.hotelbooking.domain.Payment;
import com.sepehr.hotelbooking.dto.response.PaymentResponse;
import com.sepehr.hotelbooking.exception.PaymentAlreadyExistsException;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.BookingRepository;
import com.sepehr.hotelbooking.repository.PaymentRepository;
import com.sepehr.hotelbooking.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public PaymentResponse createPayment(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId));

        if (paymentRepository.existsByBookingId(bookingId)) {
            throw new PaymentAlreadyExistsException(
                    "Payment already exists for booking id: " + bookingId);
        }

        Payment payment = new Payment(
                booking,
                booking.getTotalPrice());

        Payment savedPayment = paymentRepository.save(payment);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + id));

        return mapToResponse(payment);
    }

    @Override
    @Transactional
    public void processSuccessfulPayment(
            Long paymentId,
            String transactionId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId));

        payment.markAsSuccessful(transactionId);
    }

    @Override
    @Transactional
    public void processFailedPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId));

        payment.markAsFailed();
    }

    @Override
    @Transactional
    public void refundPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId));

        payment.refund();
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getPaidAt()
        );
    }
}