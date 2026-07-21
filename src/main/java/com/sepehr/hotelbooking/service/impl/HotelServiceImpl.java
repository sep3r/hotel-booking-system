package com.sepehr.hotelbooking.service.impl;


import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.domain.Role;
import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.dto.response.HotelResponse;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.HotelRepository;
import com.sepehr.hotelbooking.security.CurrentUserService;
import com.sepehr.hotelbooking.service.HotelService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelServiceImpl implements HotelService {

    private final CurrentUserService currentUserService;

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponse createHotel(CreateHotelRequest request) {

        User manager = currentUserService.getCurrentUser();

        if (manager.getRole() != Role.HOTEL_MANAGER) {

            throw new AccessDeniedException(
                    "Only hotel managers can create hotels"
            );
        }

        Hotel hotel = new Hotel(
                request.getHotelName(),
                request.getCity(),
                request.getAddress(),
                request.getDescription(),
                request.getStarRating(),
                request.getPhoneNumber(),
                manager
        );
        hotelRepository.save(hotel);
        return mapToResponse(hotel);
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