package com.sn.socialnetworkapp.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterDto(@NotBlank
                          String firstname,
                          String lastname,
                          @NotBlank
                          @Email(regexp = "[\\w\\.]{3,}@\\w{3,}\\.\\w{3,}")
                          String email,
                          @Length(min = 6)
                          @NotBlank
                          String password,
                          @Length(min = 6)
                          @NotBlank
                          String confirmPassword) {
}
