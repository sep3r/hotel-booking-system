package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
import com.sepehr.hotelbooking.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse createRoom(CreateRoomRequest request);

    RoomResponse getRoomById(Long id);

    List<RoomResponse> getAllRooms();

    void deleteRoom(Long id);
}