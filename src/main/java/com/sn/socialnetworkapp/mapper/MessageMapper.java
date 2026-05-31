package com.sn.socialnetworkapp.mapper;

import com.sn.socialnetworkapp.entity.Message;
import com.sn.socialnetworkapp.payload.message.MessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto toDto(Message message);
}
