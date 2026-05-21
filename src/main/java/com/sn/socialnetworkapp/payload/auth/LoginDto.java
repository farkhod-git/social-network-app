package com.sn.socialnetworkapp.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank(message = "email is required")
                       @Email(regexp = "\\w{3,}@[a-z]+\\.[a-z]+")
                       String email,
                       @NotBlank(message = "password is required")
                       String password) {
}
