package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository
        extends JpaRepository<User, Long> {

    List<User> findAllByOrderByCreatedAtDesc();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}