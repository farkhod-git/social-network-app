package com.sn.socialnetworkapp.payload.chat;

import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateChatDto(@NotBlank
                            String name,
                            @NotNull
                            ChatTypeEnum type,
                            UUID avatarId) {
}
