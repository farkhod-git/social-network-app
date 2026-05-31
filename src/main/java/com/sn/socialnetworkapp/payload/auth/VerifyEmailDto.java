package com.sn.socialnetworkapp.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record VerifyEmailDto (@NotBlank
                              @Email(regexp = "[\\w\\.]{3,}@\\w{3,}\\.\\w{3,}")
                              String email,
                              @NotBlank
                              @Length(min = 4, max = 4)
                              String code) {
}
