package com.sepehr.hotelbooking.dto.request;

import jakarta.validation.constraints.*;

public class CreateUserRequest {


    @NotBlank(message = "First name is required.")
    @Size(max = 50)
    private String firstName;


    @NotBlank(message = "Last name is required.")
    @Size(max = 50)
    private String lastName;


    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Size(max = 100)
    private String email;


    @NotBlank(message = "Password is required.")
    @Size(min = 8)
    private String password;


    @NotBlank(message = "Phone number is required.")
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