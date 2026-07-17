package com.sepehr.hotelbooking.service.impl;

import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.domain.Room;
import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
import com.sepehr.hotelbooking.dto.response.RoomResponse;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.HotelRepository;
import com.sepehr.hotelbooking.repository.RoomRepository;
import com.sepehr.hotelbooking.service.RoomService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;


    @Override
    @Transactional
    public RoomResponse createRoom(CreateRoomRequest request) {

        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Hotel not found with id: "
                                        + request.getHotelId()
                        )
                );


        Room room = new Room(
                request.getRoomNumber(),
                request.getRoomType(),
                request.getPricePerNight(),
                hotel
        );


        Room savedRoom = roomRepository.save(room);


        return mapToResponse(savedRoom);
    }


    @Override
    public RoomResponse getRoomById(Long id) {


        Room room = roomRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Room not found with id: " + id
                        )
                );


        return mapToResponse(room);
    }


    @Override
    public List<RoomResponse> getAllRooms() {


        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    @Transactional
    public void deleteRoom(Long id) {


        Room room = roomRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Room not found with id: " + id
                        )
                );


        roomRepository.delete(room);
    }


    private RoomResponse mapToResponse(Room room) {


        return new RoomResponse(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getStatus(),
                room.getHotel().getId()
        );
    }
}