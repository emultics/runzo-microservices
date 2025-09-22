package com.runzo.userservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "firstname is required")
    private String firstName;
    @NotBlank(message = "lastname is required")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "phone is required")
    @Size(min = 10, max = 10)
    private String phone;

    @NotBlank(message = "password is required")
    private String password;
}
