package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.MyPageDto;
import com.sn.socialnetworkapp.payload.ScrollPageDto;
import com.sn.socialnetworkapp.payload.chat.ChatDto;
import com.sn.socialnetworkapp.payload.chat.ChatsFilterDto;
import com.sn.socialnetworkapp.payload.chat.CreateChatDto;
import com.sn.socialnetworkapp.payload.chat.UpdateChatDto;
import com.sn.socialnetworkapp.payload.message.CreateMessageDto;
import com.sn.socialnetworkapp.payload.message.MessageDto;
import com.sn.socialnetworkapp.repository.projection.MessageProjection;
import com.sn.socialnetworkapp.service.ChatService;
import com.sn.socialnetworkapp.service.MessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @PostMapping
    public ApiResponseDto<ChatDto> createChat(@Valid @RequestBody CreateChatDto createChatDto) {
        return chatService.createChat(createChatDto);
    }

    @PostMapping("/{id}/members")
    public ApiResponseDto<ChatDto> addMembers(@PathVariable UUID id,
                                              @NotNull @NotEmpty @RequestBody Set<UUID> memberIds) {
        return chatService.addMembers(id, memberIds);
    }

    @GetMapping
    public ApiResponseDto<MyPageDto<ChatDto>> chats(@Valid ChatsFilterDto chatsFilterDto) {
        return chatService.chats(chatsFilterDto);
    }

    @PutMapping("/{id}")
    public ApiResponseDto<ChatDto> updateChat(@PathVariable UUID id,
                                              @Valid @ModelAttribute UpdateChatDto updateChatDto) {
        return chatService.updateChat(id, updateChatDto);
    }

    @PatchMapping("/{id}/avatar/{avatarId}")
    public ApiResponseDto<ChatDto> updateAvatar(@PathVariable UUID id,
                                                @PathVariable UUID avatarId) {
        return chatService.updateAvatar(id, avatarId);
    }

    @DeleteMapping("/{id}/avatar")
    public void deleteAvatar(@PathVariable UUID id) {
        chatService.deleteAvatar(id);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable UUID id) {
        chatService.deleteChat(id);
    }


    // create message
    @PostMapping("/{id}/messages")
    public ApiResponseDto<MessageDto> createMessage(@PathVariable UUID id,
                                                    @Valid @RequestBody CreateMessageDto createMessageDto) {
        return messageService.createMessage(id, createMessageDto);
    }

    // messages scroll pagination
    @GetMapping("/{id}/message")
    public ApiResponseDto<ScrollPageDto<MessageProjection>> messages(@PathVariable UUID id,
                                                                     @RequestParam Long messageId,
                                                                     @RequestParam ScrollPageDto.Direction direction,
                                                                     @RequestParam(required = false, defaultValue = "20") Integer limit) {
        return messageService.messages(id, messageId, direction, limit);
    }

}
