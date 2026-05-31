package com.sn.socialnetworkapp.payload.chat;

import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatDto {
    UUID id;
    String name;
    AttachmentDto avatar;
    ChatTypeEnum type;
    UserDto createdBy;
    List<UserDto> members;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
