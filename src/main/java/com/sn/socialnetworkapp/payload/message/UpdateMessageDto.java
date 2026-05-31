package com.sn.socialnetworkapp.payload.message;

import jakarta.validation.constraints.NotBlank;

public record UpdateMessageDto(@NotBlank String content) {
}
