package com.sepehr.hotelbooking.dto.response;


import com.sepehr.hotelbooking.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;
}