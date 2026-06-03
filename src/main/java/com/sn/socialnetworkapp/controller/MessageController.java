package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.message.UpdateMessageDto;
import com.sn.socialnetworkapp.payload.message.MessageDto;
import com.sn.socialnetworkapp.payload.messageread.MessageReadDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import com.sn.socialnetworkapp.service.MessageReadService;
import com.sn.socialnetworkapp.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageReadService messageReadService;

    @PutMapping("/{id}")
    ApiResponseDto<MessageDto> updateMessage(@PathVariable Long id,
                                             @Valid @RequestBody UpdateMessageDto updateMessageDto) {
        return messageService.updateMessage(id, updateMessageDto);
    }

    @DeleteMapping("/{id}")
    void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }


    @GetMapping("/{id}/reads")
    public ApiResponseDto<List<MessageReadDto>> reads(@PathVariable Long id) {
        return messageReadService.reads(id);
    }
}
