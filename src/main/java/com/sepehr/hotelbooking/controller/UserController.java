package com.sepehr.hotelbooking.controller;


import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateUserRequest;
import com.sepehr.hotelbooking.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @PostMapping
    public ResponseEntity<User> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        User user = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id
    ) {

        User user = userService.getUserById(id);

        return ResponseEntity
                .ok(user);
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity
                .ok(
                        userService.getAllUsers()
                );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {

        userService.deleteUser(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}