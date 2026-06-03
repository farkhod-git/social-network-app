package com.sn.socialnetworkapp.payload.messageread;

import com.sn.socialnetworkapp.payload.user.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageReadDto {
    UUID id;
    UserDto user;
    LocalDateTime createdAt;
}
