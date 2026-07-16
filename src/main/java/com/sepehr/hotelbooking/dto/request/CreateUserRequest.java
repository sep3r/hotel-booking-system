package com.sepehr.hotelbooking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class CreateUserRequest {


    @NotBlank(message = "First name is required.")
    @Size(
            max = 50,
            message = "First name must not exceed 50 characters."
    )
    private String firstName;


    @NotBlank(message = "Last name is required.")
    @Size(
            max = 50,
            message = "Last name must not exceed 50 characters."
    )
    private String lastName;


    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Size(
            max = 150,
            message = "Email must not exceed 150 characters."
    )
    private String email;


    @NotBlank(message = "Password is required.")
    @Size(
            min = 8,
            max = 100,
            message = "Password must be between 8 and 100 characters."
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
    private String phoneNumber;


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }
}