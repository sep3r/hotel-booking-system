package com.sepehr.hotelbooking.security;


import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Current user not found"
                        )
                );
    }
}