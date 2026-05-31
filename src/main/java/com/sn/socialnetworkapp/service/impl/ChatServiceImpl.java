package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import com.sn.socialnetworkapp.exceptions.MyBadRequestException;
import com.sn.socialnetworkapp.exceptions.MyConflictException;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.mapper.ChatMapper;
import com.sn.socialnetworkapp.mapper.PageMapper;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.MyPageDto;
import com.sn.socialnetworkapp.payload.chat.ChatDto;
import com.sn.socialnetworkapp.payload.chat.ChatsFilterDto;
import com.sn.socialnetworkapp.payload.chat.CreateChatDto;
import com.sn.socialnetworkapp.payload.chat.UpdateChatDto;
import com.sn.socialnetworkapp.repository.AttachmentRepository;
import com.sn.socialnetworkapp.repository.ChatRepository;
import com.sn.socialnetworkapp.repository.UserRepository;
import com.sn.socialnetworkapp.service.ChatService;
import com.sn.socialnetworkapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;
    private final PageMapper pageMapper;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResponseDto<ChatDto> createChat(CreateChatDto createChatDto) {
        User currentUser = CurrentUserUtil.getCurrentUser();

        List<UUID> memberIds = createChatDto.memberIds();
        if (memberIds.contains(currentUser.getId())) {
            throw new MyConflictException("Current User is already member of this chat");
        }

        // private chat
        if (createChatDto.type() == ChatTypeEnum.PRIVATE) {
            if (memberIds.size() > 1) {
                throw new MyBadRequestException("Private chat can contain only one member");
            }

            boolean exists = chatRepository.existsPrivateChat(currentUser.getId(), memberIds.getFirst());
            if (exists) {
                throw new MyBadRequestException("Private chat already exists");
            }
        }

        List<User> members = new ArrayList<>(userRepository.findAllById(memberIds));
        if (members.size() != memberIds.size()) {
            throw new MyNotFoundException("Some members not found");
        }

        // self
        members.add(currentUser);

        Chat chat = chatMapper.toEntity(createChatDto);
        chat.setMembers(members);

        // try to save avatar
        if (createChatDto.avatarId() != null) {
            attachmentRepository.findById(createChatDto.avatarId())
                    .ifPresent(chat::setAvatar);
        }

        chatRepository.save(chat);

        ChatDto chatDto = chatMapper.toDto(chat);

        return ApiResponseDto.success(chatDto);
    }

    @Override
    public ApiResponseDto<ChatDto> addMembers(UUID id, Set<UUID> memberIds) {
        Chat chat = chatRepository.findByIdAndCreatedByAndType(id, CurrentUserUtil.getCurrentUser(), ChatTypeEnum.GROUP)
                .orElseThrow(() -> new MyNotFoundException("Group not found"));

        for (User member : chat.getMembers()) {
            if (memberIds.contains(member.getId())) {
                throw new MyConflictException(member.getId() + " ID Member already exists");
            }
        }

        List<User> newMembers = userRepository.findAllById(memberIds);
        ArrayList<User> members = new ArrayList<>(chat.getMembers());
        members.addAll(newMembers);

        chat.setMembers(members);

        chatRepository.save(chat);

        return ApiResponseDto.success(chatMapper.toDto(chat));
    }

    @Override
    public ApiResponseDto<MyPageDto<ChatDto>> chats(ChatsFilterDto chatsFilterDto) {
        User currentUser = CurrentUserUtil.getCurrentUser();

        final PageRequest pageRequest = PageRequest.of(chatsFilterDto.getPage(), chatsFilterDto.getSize());

        Page<Chat> chatPage;
        String search = chatsFilterDto.getSearch();
        if (StringUtils.hasLength(search)) {
            chatPage = chatRepository.findAllByNameStartsWith(search, pageRequest);
        } else {
            chatPage = chatRepository.findAllByCreatedBy(currentUser, pageRequest);
        }

        MyPageDto<ChatDto> chatPageDto = pageMapper.toCustomPageDto(chatPage);
        chatPageDto.setContent(chatMapper.toDtoList(chatPage.getContent()));

        return ApiResponseDto.success(chatPageDto);
    }

    @Override
    public ApiResponseDto<ChatDto> updateChat(UUID id, UpdateChatDto updateChatDto) {
        User currentUser = CurrentUserUtil.getCurrentUser();

        Chat chat = chatRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new MyNotFoundException("Chat not found"));

        chat.setName(updateChatDto.getName());

        chatRepository.save(chat);

        return ApiResponseDto.success(chatMapper.toDto(chat));
    }

    @Override
    public ApiResponseDto<ChatDto> updateAvatar(UUID id, UUID avatarId) {
        User currentUser = CurrentUserUtil.getCurrentUser();

        Chat chat = chatRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new MyNotFoundException("Chat not found"));

        Attachment attachment = attachmentRepository.findById(avatarId)
                        .orElseThrow(() -> new MyNotFoundException("Attachment not found"));

        chat.setAvatar(attachment);
        chatRepository.save(chat);

        return ApiResponseDto.success(chatMapper.toDto(chat));
    }

    @Override
    public void deleteAvatar(UUID id) {
        User currentUser = CurrentUserUtil.getCurrentUser();

        Chat chat = chatRepository.findByIdAndCreatedBy(id, currentUser)
                .orElseThrow(() -> new MyNotFoundException("Chat not found"));

        chat.setAvatar(null);

        chatRepository.save(chat);
    }

    @Override
    public void deleteChat(UUID id) {
        User currentUser = CurrentUserUtil.getCurrentUser();

        boolean exists = chatRepository.existsByIdAndCreatedBy(id, currentUser);
        if (!exists) {
            throw new MyNotFoundException("Chat not found");
        }

        chatRepository.deleteById(id);
    }
}
