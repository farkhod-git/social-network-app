package com.sn.socialnetworkapp.payload.chat;

import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

public record CreateChatDto(@NotBlank
                            String name,
                            @NotNull
                            ChatTypeEnum type,
                            @NotNull
                            List<UUID> memberIds,
                            UUID avatarId) {
}
