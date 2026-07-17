package com.sepehr.hotelbooking.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_email",
                        columnNames = "email"
                )
        }
)
public class User {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq_generator"
    )
    @SequenceGenerator(
            name = "user_seq_generator",
            sequenceName = "user_seq",
            allocationSize = 50,
            initialValue = 1
    )
    private Long id;


    @NotBlank
    @Column(
            name = "first_name",
            nullable = false,
            length = 50
    )
    private String firstName;


    @NotBlank
    @Column(
            name = "last_name",
            nullable = false,
            length = 50
    )
    private String lastName;


    @NotBlank
    @Email
    @Column(
            nullable = false,
            length = 150
    )
    private String email;


    @NotBlank
    @Column(
            nullable = false,
            length = 100
    )
    private String password;


    @NotBlank
    @Column(
            name = "phone_number",
            nullable = false,
            length = 20
    )
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 30
    )
    private Role role;


    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;


    public User(
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber
    ) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = Role.CUSTOMER;
        this.createdAt = LocalDateTime.now();
    }


    public void changePassword(String encodedPassword) {

        this.password = encodedPassword;
    }


    public void changeRole(Role role) {

        this.role = role;
    }
}