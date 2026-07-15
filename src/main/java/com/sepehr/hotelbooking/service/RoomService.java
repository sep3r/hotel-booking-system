package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.domain.Room;
import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;

import java.util.List;


public interface RoomService {


    Room createRoom(CreateRoomRequest request);


    Room getRoomById(Long id);


    List<Room> getAllRooms();


    void deleteRoom(Long id);
}