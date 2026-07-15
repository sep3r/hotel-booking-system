package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.domain.Hotel;

import java.util.List;

public interface HotelService {


    Hotel createHotel(CreateHotelRequest request);


    Hotel getHotelById(Long id);


    List<Hotel> getAllHotels();


    void deleteHotel(Long id);
}