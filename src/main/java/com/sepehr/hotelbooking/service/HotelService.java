package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.dto.request.CreateHotelRequest;
import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {


    HotelResponse createHotel(CreateHotelRequest request);


    HotelResponse getHotelById(Long id);


    List<HotelResponse> getAllHotels();


    void deleteHotel(Long id);
}