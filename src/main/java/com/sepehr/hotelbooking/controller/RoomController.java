package com.sepehr.hotelbooking.controller;

import com.sepehr.hotelbooking.dto.request.CreateRoomRequest;
import com.sepehr.hotelbooking.dto.response.RoomResponse;
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
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody CreateRoomRequest request
    ) {

        RoomResponse response = roomService.createRoom(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable Long id
    ) {

        return ResponseEntity
                .ok(
                        roomService.getRoomById(id)
                );
    }


    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {

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