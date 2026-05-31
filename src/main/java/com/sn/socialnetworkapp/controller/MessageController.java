package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.message.UpdateMessageDto;
import com.sn.socialnetworkapp.payload.message.MessageDto;
import com.sn.socialnetworkapp.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PutMapping("/{id}")
    ApiResponseDto<MessageDto> updateMessage(@PathVariable Long id,
                                             @Valid @RequestBody UpdateMessageDto updateMessageDto) {
        return messageService.updateMessage(id, updateMessageDto);
    }

    @DeleteMapping("/{id}")
    void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

}
