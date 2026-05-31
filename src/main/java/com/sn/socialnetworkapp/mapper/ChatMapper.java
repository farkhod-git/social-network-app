package com.sn.socialnetworkapp.mapper;

import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.payload.chat.ChatDto;
import com.sn.socialnetworkapp.payload.chat.CreateChatDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    Chat toEntity(CreateChatDto createChatDto);

    ChatDto toDto(Chat chat);

    List<ChatDto> toDtoList(List<Chat> chats);

}
