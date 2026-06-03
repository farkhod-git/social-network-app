package com.sn.socialnetworkapp.payload.user;

import com.sn.socialnetworkapp.payload.attachment.AttachmentDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    UUID id;
    String firstname;
    String lastname;
    String email;
    AttachmentDto avatar;
}
