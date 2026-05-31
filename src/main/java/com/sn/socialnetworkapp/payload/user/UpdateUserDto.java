package com.sn.socialnetworkapp.payload.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(@NotBlank String firstname,
                            String lastname) {
}