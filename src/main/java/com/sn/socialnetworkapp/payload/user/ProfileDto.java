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
public class ProfileDto {
    String firstname;
    String lastname;
    AttachmentDto avatar;
}
