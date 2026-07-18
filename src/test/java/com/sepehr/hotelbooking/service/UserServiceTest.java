package com.sepehr.hotelbooking.service;

import com.sepehr.hotelbooking.domain.User;
import com.sepehr.hotelbooking.dto.request.CreateUserRequest;
import com.sepehr.hotelbooking.dto.response.UserResponse;
import com.sepehr.hotelbooking.exception.ResourceNotFoundException;
import com.sepehr.hotelbooking.repository.UserRepository;
import com.sepehr.hotelbooking.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;


class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    private User user;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        user = new User(
                "Sepehr",
                "Mirza",
                "sepehr@test.com",
                "password123",
                "+989121234567"
        );

    }

    @Test
    void shouldCreateUserSuccessfully() {

        CreateUserRequest request =
                new CreateUserRequest(
                        "Sepehr",
                        "Mirza",
                        "sepehr@test.com",
                        "password123",
                        "+989121234567"
                );

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponse response =
                userService.createUser(request);

        assertThat(response)
                .isNotNull();

        assertThat(response.getEmail())
                .isEqualTo("sepehr@test.com");

        verify(userRepository)
                .save(any(User.class));

    }

    @Test
    void shouldGetUserByIdSuccessfully() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponse response =
                userService.getUserById(1L);

        assertThat(response)
                .isNotNull();

        assertThat(response.getEmail())
                .isEqualTo("sepehr@test.com");

        verify(userRepository)
                .findById(1L);

    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> userService.getUserById(99L)
        )
                .isInstanceOf(ResourceNotFoundException.class);

    }


    @Test
    void shouldGetAllUsersSuccessfully() {

        when(userRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(user));

        List<UserResponse> response =
                userService.getAllUsers();

        assertThat(response)
                .isNotEmpty();

        assertThat(response.size())
                .isEqualTo(1);

        assertThat(response.get(0).getEmail())
                .isEqualTo("sepehr@test.com");

        verify(userRepository)
                .findAllByOrderByCreatedAtDesc();

    }

    @Test
    void shouldDeleteUserSuccessfully() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository)
                .delete(user);

    }

    @Test
    void shouldThrowExceptionWhenDeletingUserNotFound() {

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> userService.deleteUser(99L)
        )
                .isInstanceOf(ResourceNotFoundException.class);

        verify(userRepository, never())
                .delete(any(User.class));

    }
}