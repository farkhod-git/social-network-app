package com.sn.socialnetworkapp.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.sn.socialnetworkapp.util.AppConstants.EMAIL_PATTERN;

public record ResetPasswordDto(@NotBlank
                               @Email(regexp = EMAIL_PATTERN) String email) {
}
