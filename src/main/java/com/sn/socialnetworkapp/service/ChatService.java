package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.MyPageDto;
import com.sn.socialnetworkapp.payload.chat.ChatDto;
import com.sn.socialnetworkapp.payload.chat.ChatsFilterDto;
import com.sn.socialnetworkapp.payload.chat.CreateChatDto;
import com.sn.socialnetworkapp.payload.chat.UpdateChatDto;

import java.util.Set;
import java.util.UUID;

public interface ChatService {
    ApiResponseDto<ChatDto> createChat(CreateChatDto createChatDto);

    ApiResponseDto<MyPageDto<ChatDto>> chats(ChatsFilterDto chatsFilterDto);

    void deleteChat(UUID id);

    ApiResponseDto<ChatDto> updateChat(UUID id, UpdateChatDto updateChatDto);

    void deleteAvatar(UUID id);

    ApiResponseDto<ChatDto> addMembers(UUID id, Set<UUID> memberIds);

    ApiResponseDto<ChatDto> updateAvatar(UUID id, UUID avatarId);
}
