package com.sn.socialnetworkapp.payload.message;

import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    Long id;
    String content;
    MessageDto replyMessage;
    AttachmentDto media;
    UserDto createdBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
