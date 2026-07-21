package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelRepository
        extends JpaRepository<Hotel, Long> {

    List<Hotel> findAllByManagerId(Long managerId);
}