package com.sn.socialnetworkapp.payload.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatsFilterDto {
    int page = 0;
    int size = 20;
    String search = null;
}
