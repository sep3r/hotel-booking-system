package com.sepehr.hotelbooking.service.impl;


import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.LoginRequest;
import com.sepehr.hotelbooking.dto.request.RegisterRequest;
import com.sepehr.hotelbooking.dto.response.AuthResponse;
import com.sepehr.hotelbooking.repository.UserRepository;
import com.sepehr.hotelbooking.security.JwtService;
import com.sepehr.hotelbooking.service.AuthService;
import com.sepehr.hotelbooking.exception.EmailAlreadyExistsException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(
                    "Email already registered."
            );
        }
        User user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.phoneNumber()
        );
        userRepository.save(user);
        return new AuthResponse(
                jwtService.generateToken(request.email())
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        String token =
                jwtService.generateToken(
                        request.email()
                );
        return new AuthResponse(
                token
        );
    }
}