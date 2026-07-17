package com.sepehr.hotelbooking.service;


import com.sepehr.hotelbooking.dto.request.CreateUserRequest;
import com.sepehr.hotelbooking.dto.response.UserResponse;

import java.util.List;


public interface UserService {


    UserResponse createUser(CreateUserRequest request);


    UserResponse getUserById(Long id);


    List<UserResponse> getAllUsers();


    void deleteUser(Long id);
}