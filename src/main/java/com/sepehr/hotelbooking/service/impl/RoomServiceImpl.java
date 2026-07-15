package com.sepehr.hotelbooking.service.impl;

import com.sepehr.hotelbooking.domain.Hotel;
import com.sepehr.hotelbooking.domain.Room;
import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
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
    public Room createRoom(CreateRoomRequest request) {


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


        return roomRepository.save(room);
    }


    @Override
    public Room getRoomById(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Room not found with id: " + id
                        )
                );
    }


    @Override
    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteRoom(Long id) {

        Room room = getRoomById(id);

        roomRepository.delete(room);
    }
}