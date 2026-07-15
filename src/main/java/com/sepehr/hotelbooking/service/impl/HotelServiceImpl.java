package com.sepehr.hotelbooking.service.impl;

import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.repository.HotelRepository;
import com.sepehr.hotelbooking.service.HotelService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelServiceImpl implements HotelService {


    private final HotelRepository hotelRepository;


    @Override
    @Transactional
    public Hotel createHotel(CreateHotelRequest request) {

        Hotel hotel = new Hotel(
                request.getHotelName(),
                request.getCity(),
                request.getAddress(),
                request.getDescription(),
                request.getStarRating(),
                request.getPhoneNumber()
        );
        return hotelRepository.save(hotel);
    }


    @Override
    public Hotel getHotelById(Long id) {

        return hotelRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Hotel not found with id: " + id
                        )
                );
    }


    @Override
    public List<Hotel> getAllHotels() {

        return hotelRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteHotel(Long id) {

        Hotel hotel = getHotelById(id);

        hotelRepository.delete(hotel);
    }
}