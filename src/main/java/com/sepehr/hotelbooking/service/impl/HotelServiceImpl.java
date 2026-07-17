package com.sepehr.hotelbooking.service.impl;


import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.dto.response.HotelResponse;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
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
    public HotelResponse createHotel(CreateHotelRequest request) {


        Hotel hotel = new Hotel(
                request.getHotelName(),
                request.getCity(),
                request.getAddress(),
                request.getDescription(),
                request.getStarRating(),
                request.getPhoneNumber()
        );


        Hotel savedHotel = hotelRepository.save(hotel);


        return mapToResponse(savedHotel);
    }


    @Override
    public HotelResponse getHotelById(Long id) {


        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Hotel not found with id: " + id
                        )
                );


        return mapToResponse(hotel);
    }


    @Override
    public List<HotelResponse> getAllHotels() {


        return hotelRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    @Transactional
    public void deleteHotel(Long id) {


        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Hotel not found with id: " + id
                        )
                );


        hotelRepository.delete(hotel);
    }


    private HotelResponse mapToResponse(Hotel hotel) {


        return new HotelResponse(
                hotel.getId(),
                hotel.getHotelName(),
                hotel.getCity(),
                hotel.getAddress(),
                hotel.getDescription(),
                hotel.getStarRating(),
                hotel.getPhoneNumber()
        );
    }
}