package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelRepository
        extends JpaRepository<Hotel, Long> {

}