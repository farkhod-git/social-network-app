package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.ScrollPageDto;
import com.sn.socialnetworkapp.payload.message.CreateMessageDto;
import com.sn.socialnetworkapp.payload.message.MessageDto;
import com.sn.socialnetworkapp.payload.message.UpdateMessageDto;
import com.sn.socialnetworkapp.repository.projection.MessageProjection;

import java.util.UUID;

public interface MessageService {

    ApiResponseDto<MessageDto> createMessage(UUID chatId, CreateMessageDto createMessageDto);

    ApiResponseDto<ScrollPageDto<MessageProjection>> messages(UUID id, Long messageId, ScrollPageDto.Direction direction, Integer limit);

    ApiResponseDto<MessageDto> updateMessage(Long id, UpdateMessageDto updateMessageDto);

    void deleteMessage(Long id);
}
