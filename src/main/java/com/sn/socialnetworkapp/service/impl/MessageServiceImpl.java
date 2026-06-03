package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.entity.Message;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.exceptions.MyBadRequestException;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.mapper.MessageMapper;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.ScrollPageDto;
import com.sn.socialnetworkapp.payload.message.CreateMessageDto;
import com.sn.socialnetworkapp.payload.message.MessageDto;
import com.sn.socialnetworkapp.payload.message.UpdateMessageDto;
import com.sn.socialnetworkapp.repository.AttachmentRepository;
import com.sn.socialnetworkapp.repository.ChatRepository;
import com.sn.socialnetworkapp.repository.MessageRepository;
import com.sn.socialnetworkapp.repository.projection.MessageProjection;
import com.sn.socialnetworkapp.service.MessageReadService;
import com.sn.socialnetworkapp.service.MessageService;
import com.sn.socialnetworkapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final AttachmentRepository attachmentRepository;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageReadService messageReadService;

    @Override
    public ApiResponseDto<MessageDto> createMessage(UUID chatId, CreateMessageDto createMessageDto) {
        if (createMessageDto.content() == null && createMessageDto.mediaId() == null) {
            throw new MyBadRequestException("Content and mediaId can't be empty");
        }

        User currentUser = CurrentUserUtil.getCurrentUser();

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new MyNotFoundException("Chat not found"));

        Attachment media = null;
        if (createMessageDto.mediaId() != null) {
            media = attachmentRepository
                    .findById(createMessageDto.mediaId())
                    .orElseThrow(() -> new MyNotFoundException("Attachment not found"));
        }

        Message replyMessage = null;
        if (createMessageDto.replyMessageId() != null) {
            replyMessage = messageRepository
                    .findById(createMessageDto.replyMessageId())
                    .orElseThrow(() -> new MyNotFoundException("Reply Message not found"));
        }

        Message message = new Message();
        message.setChat(chat);
        message.setMedia(media);
        message.setReplyMessage(replyMessage);
        message.setContent(createMessageDto.content());
        messageRepository.save(message);

        MessageDto messageDto = messageMapper.toDto(message);
        messageDto.setReplyMessage(messageMapper.toDto(message.getReplyMessage()));
        return ApiResponseDto.success(messageDto);
    }

    @Override
    public ApiResponseDto<ScrollPageDto<MessageProjection>> messages(UUID chatId, Long messageId, ScrollPageDto.Direction direction, Integer limit) {
        boolean exists = chatRepository.existsById(chatId);
        if (!exists) {
            throw new MyNotFoundException("Chat not found");
        }

        final List<MessageProjection> messages;

        if (direction == ScrollPageDto.Direction.DOWN)
            messages = messageRepository.scrollDown(chatId, messageId, limit);
        else messages = messageRepository.scrollUp(chatId, messageId, limit);

        List<Long> messageIds = messages.stream()
                .map(MessageProjection::getId)
                .toList();

        messageReadService.read(messageIds);

        return ApiResponseDto.success(new ScrollPageDto<>(messages, direction));
    }

    @Override
    public ApiResponseDto<MessageDto> updateMessage(Long id, UpdateMessageDto updateMessageDto) {
        Message message = messageRepository.findByIdAndCreatedBy(id, CurrentUserUtil.getCurrentUser())
                .orElseThrow(() -> new MyNotFoundException("Message not found"));

        message.setContent(updateMessageDto.content());
        messageRepository.save(message);

        return ApiResponseDto.success(messageMapper.toDto(message));
    }

    @Override
    public void deleteMessage(Long id) {
        Message message = messageRepository.findByIdAndCreatedBy(id, CurrentUserUtil.getCurrentUser())
                .orElseThrow(() -> new MyNotFoundException("Message not found"));

        messageRepository.delete(message);
    }
}
