package com.sn.socialnetworkapp.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank(message = "email inputStream required")
                       @Email(regexp = "[\\w\\.]{3,}@\\w+\\.\\w+")
                       String email,
                       @NotBlank(message = "password inputStream required")
                       String password) {
}
