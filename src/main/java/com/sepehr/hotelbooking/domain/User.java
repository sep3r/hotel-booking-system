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


    @NotBlank(message = "First name is required.")
    @Size(
            max = 50,
            message = "First name must not exceed 50 characters."
    )
    @Column(
            name = "first_name",
            nullable = false,
            length = 50
    )
    private String firstName;


    @NotBlank(message = "Last name is required.")
    @Size(
            max = 50,
            message = "Last name must not exceed 50 characters."
    )
    @Column(
            name = "last_name",
            nullable = false,
            length = 50
    )
    private String lastName;


    @NotBlank(message = "Email is required.")
    @Email(
            message = "Invalid email format."
    )
    @Size(
            max = 150,
            message = "Email must not exceed 150 characters."
    )
    @Column(
            nullable = false,
            length = 150
    )
    private String email;


    @NotBlank(message = "Password is required.")
    @Size(
            min = 8,
            max = 100,
            message = "Password must be between 8 and 100 characters."
    )
    @Column(
            nullable = false,
            length = 100
    )
    private String password;


    @NotBlank(message = "Phone number is required.")
    @Size(
            max = 20,
            message = "Phone number must not exceed 20 characters."
    )
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Invalid phone number format."
    )
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
            String phoneNumber,
            Role role
    ) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }


    public void changePassword(String encodedPassword) {

        this.password = encodedPassword;
    }


    public void changeRole(Role role) {

        this.role = role;
    }
}