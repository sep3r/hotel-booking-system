package com.sepehr.hotelbooking.service;


import com.sepehr.hotelbooking.domain.*;

import com.sepehr.hotelbooking.dto.response.PaymentResponse;

import com.sepehr.hotelbooking.exception.ResourceNotFoundException;

import com.sepehr.hotelbooking.repository.BookingRepository;
import com.sepehr.hotelbooking.repository.PaymentRepository;

import com.sepehr.hotelbooking.service.impl.PaymentServiceImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;



class PaymentServiceTest {



    @Mock
    private PaymentRepository paymentRepository;



    @Mock
    private BookingRepository bookingRepository;



    @InjectMocks
    private PaymentServiceImpl paymentService;



    private User user;

    private Hotel hotel;

    private Room room;

    private Booking booking;

    private Payment payment;




    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        user = new User(
                "Sepehr",
                "Mirza",
                "manager@test.com",
                "password",
                "09123456789"
        );

        user.changeRole(Role.HOTEL_MANAGER);

        Hotel hotel = new Hotel(
                "Hilton",
                "Berlin",
                "Address",
                "Description",
                5,
                "123456",
                user
        );

        room = new Room(
                "101",
                RoomType.DOUBLE,
                BigDecimal.valueOf(100),
                hotel
        );

        booking = new Booking(
                user,
                room,
                LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 8, 4),
                BigDecimal.valueOf(300)
        );

        payment = new Payment(
                booking,
                BigDecimal.valueOf(300)
        );
    }

    @Test
    void shouldCreatePaymentSuccessfully() {
        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));
        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);
        PaymentResponse response =
                paymentService.createPayment(1L);
        assertThat(response)
                .isNotNull();
        assertThat(response.getAmount())
                .isEqualTo(BigDecimal.valueOf(300));
        verify(bookingRepository)
                .findById(1L);
        verify(paymentRepository)
                .save(any(Payment.class));
    }

    @Test
    void shouldThrowExceptionWhenBookingNotFound() {
        when(bookingRepository.findById(99L))
                .thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> paymentService.createPayment(99L)
        )
                .isInstanceOf(ResourceNotFoundException.class);
        verify(paymentRepository, never())
                .save(any());
    }

    @Test
    void shouldGetPaymentByIdSuccessfully() {
        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));
        PaymentResponse response =
                paymentService.getPaymentById(1L);
        assertThat(response)
                .isNotNull();
        assertThat(response.getAmount())
                .isEqualTo(BigDecimal.valueOf(300));
        verify(paymentRepository)
                .findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {
        when(paymentRepository.findById(10L))
                .thenReturn(Optional.empty());
        assertThatThrownBy(
                () -> paymentService.getPaymentById(10L)
        )
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldProcessSuccessfulPayment() {
        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));
        paymentService.processSuccessfulPayment(
                1L,
                "TX-12345"
        );
        assertThat(payment.getStatus())
                .isEqualTo(PaymentStatus.SUCCESS);
    }

    @Test
    void shouldProcessFailedPayment() {
        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));
        paymentService.processFailedPayment(1L);
        assertThat(payment.getStatus())
                .isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    void shouldRefundPayment() {
        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));
        paymentService.refundPayment(1L);
        assertThat(payment.getStatus())
                .isEqualTo(PaymentStatus.REFUNDED);
    }
}