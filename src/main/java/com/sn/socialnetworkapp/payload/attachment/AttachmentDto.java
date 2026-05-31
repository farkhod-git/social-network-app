package com.sn.socialnetworkapp.payload.attachment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentDto {
    UUID id;
    String originalName;
    String contentType;
    long size;
    LocalDateTime createdAt;
}