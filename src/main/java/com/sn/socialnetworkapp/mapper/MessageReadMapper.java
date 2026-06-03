package com.sn.socialnetworkapp.mapper;

import com.sn.socialnetworkapp.entity.MessageRead;
import com.sn.socialnetworkapp.payload.messageread.MessageReadDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageReadMapper {
    List<MessageReadDto> toDtoList(List<MessageRead> messageReads);
}
