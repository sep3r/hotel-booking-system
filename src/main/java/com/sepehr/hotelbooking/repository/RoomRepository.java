package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository
        extends JpaRepository<Room, Long> {


}