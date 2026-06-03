package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.Message;
import com.sn.socialnetworkapp.entity.MessageRead;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.mapper.MessageReadMapper;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.messageread.MessageReadDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import com.sn.socialnetworkapp.repository.MessageReadRepository;
import com.sn.socialnetworkapp.repository.MessageRepository;
import com.sn.socialnetworkapp.service.MessageReadService;
import com.sn.socialnetworkapp.util.CurrentUserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageReadServiceImpl implements MessageReadService {
    private final MessageReadRepository messageReadRepository;
    private final MessageRepository messageRepository;
    private final MessageReadMapper messageReadMapper;

    @Override
    public ApiResponseDto<List<MessageReadDto>> reads(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Message not found"));

        List<MessageRead> messageReads = messageReadRepository.findAllByMessage(message);
        List<MessageReadDto> messageReadDtoList = messageReadMapper.toDtoList(messageReads);

        return ApiResponseDto.success(messageReadDtoList);
    }

    @Async
    @Transactional
    @Override
    public void read(List<Long> messageIds) {
//        User currentUser = CurrentUserUtil.getCurrentUser();
//        List<Message> messages = messageRepository.findAllById(messageIds);
//
//        List<MessageRead> messageReads = messages.stream()
//                .map(message -> {
//                    MessageRead messageRead = new MessageRead();
//                    messageRead.setMessage(message);
//                    messageRead.setUser(currentUser);
//                    return messageRead;
//                }).toList();
//
//        messageReadRepository.saveAll(messageReads);
    }
}
