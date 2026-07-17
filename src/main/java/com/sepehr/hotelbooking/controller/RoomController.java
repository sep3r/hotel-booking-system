package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.domain.Room;
import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
import com.sepehr.hotelbooking.service.RoomService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {


    private final RoomService roomService;


    @PostMapping
    public ResponseEntity<Room> createRoom(
            @Valid @RequestBody CreateRoomRequest request
    ) {

        Room room = roomService.createRoom(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(room);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(
            @PathVariable Long id
    ) {

        Room room = roomService.getRoomById(id);

        return ResponseEntity
                .ok(room);
    }


    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {

        return ResponseEntity
                .ok(
                        roomService.getAllRooms()
                );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long id
    ) {

        roomService.deleteRoom(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}