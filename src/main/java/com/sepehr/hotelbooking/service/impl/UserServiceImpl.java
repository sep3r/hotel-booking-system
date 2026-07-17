package com.sepehr.hotelbooking.service.impl;


import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateUserRequest;
import com.sepehr.hotelbooking.dto.response.UserResponse;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.UserRepository;
import com.sepehr.hotelbooking.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {


        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber()
        );


        User savedUser = userRepository.save(user);


        return mapToResponse(savedUser);
    }


    @Override
    public UserResponse getUserById(Long id) {


        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );


        return mapToResponse(user);
    }


    @Override
    public List<UserResponse> getAllUsers() {


        return userRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {


        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );


        userRepository.delete(user);
    }


    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }
}