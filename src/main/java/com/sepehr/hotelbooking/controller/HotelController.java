package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.dto.response.HotelResponse;
import com.sepehr.hotelbooking.service.HotelService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(
            @Valid @RequestBody CreateHotelRequest request
    ) {
        HotelResponse hotel = hotelService.createHotel(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(
            @PathVariable Long id
    ) {
        HotelResponse hotel = hotelService.getHotelById(id);
        return ResponseEntity
                .ok(hotel);
    }


    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {

        return ResponseEntity
                .ok(
                        hotelService.getAllHotels()
                );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(
            @PathVariable Long id
    ) {
        hotelService.deleteHotel(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}