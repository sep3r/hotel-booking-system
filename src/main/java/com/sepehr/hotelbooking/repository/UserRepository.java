package com.sepehr.hotelbooking.repository;

import com.sepehr.hotelbooking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository
        extends JpaRepository<User, Long> {


}