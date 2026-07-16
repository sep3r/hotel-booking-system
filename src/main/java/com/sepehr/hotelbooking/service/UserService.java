package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateUserRequest;

import java.util.List;


public interface UserService {


    User createUser(CreateUserRequest request);


    User getUserById(Long id);


    User getUserByEmail(String email);


    List<User> getAllUsers();


    void deleteUser(Long id);
}