package com.sepehr.hotelbooking.service.impl;


import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateUserRequest;
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
    public User createUser(CreateUserRequest request) {


        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        );


        return userRepository.save(user);
    }


    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }


    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found with email: " + email
                        )
                );
    }


    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {

        User user = getUserById(id);

        userRepository.delete(user);
    }
}