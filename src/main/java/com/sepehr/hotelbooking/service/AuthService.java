package com.sepehr.hotelbooking.service;


import com.sepehr.hotelbooking.dto.request.LoginRequest;
import com.sepehr.hotelbooking.dto.request.RegisterRequest;
import com.sepehr.hotelbooking.dto.response.AuthResponse;


public interface AuthService {


    AuthResponse register(RegisterRequest request);


    AuthResponse login(LoginRequest request);

}