package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.entity.ChatMember;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import com.sn.socialnetworkapp.exceptions.MyBadRequestException;
import com.sn.socialnetworkapp.exceptions.MyConflictException;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.mapper.ChatMapper;
import com.sn.socialnetworkapp.mapper.PageMapper;
import com.sn.socialnetworkapp.mapper.UserMapper;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.MyPageDto;
import com.sn.socialnetworkapp.payload.chat.ChatDto;
import com.sn.socialnetworkapp.payload.chat.ChatsFilterDto;
import com.sn.socialnetworkapp.payload.chat.CreateChatDto;
import com.sn.socialnetworkapp.payload.chat.UpdateChatDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import com.sn.socialnetworkapp.repository.AttachmentRepository;
import com.sn.socialnetworkapp.repository.ChatMemberRepository;
import com.sn.socialnetworkapp.repository.ChatRepository;
import com.sn.socialnetworkapp.repository.UserRepository;
import com.sn.socialnetworkapp.service.ChatService;
import com.sn.socialnetworkapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;
    private final PageMapper pageMapper;
    private final AttachmentRepository attachmentRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final UserMapper userMapper;

    @Override
    public ApiResponseDto<ChatDto> createChat(CreateChatDto createChatDto) {
        // save chat
        Chat chat = chatMapper.toEntity(createChatDto);
        if (createChatDto.avatarId() != null) {
            Attachment attachment = attachmentRepository.findById(createChatDto.avatarId())
                    .orElseThrow(() -> new MyNotFoundException("Attachment not found"));
            chat.setAvatar(attachment);
        }
        chatRepository.save(chat);

        ChatMember chatMember = new ChatMember();
        chatMember.setChat(chat);
        chatMember.setMember(CurrentUserUtil.getCurrentUser());
        chatMemberRepository.save(chatMember);

        return ApiResponseDto.success(chatMapper.toDto(chat));
    }

    @Override
    public ApiResponseDto<ChatDto> addMembers(UUID id, Set<UUID> memberIds) {
        Chat chat = chatRepository.findByIdAndCreatedByAndType(id, CurrentUserUtil.getCurrentUser(), ChatTypeEnum.GROUP)
                .orElseThrow(() -> new MyNotFoundException("Group not found"));

        List<User> users = userRepository.findAllById(memberIds);
        if (users.size() != memberIds.size()) {
            throw new MyBadRequestException("Some Users not found");
        }

        List<ChatMember> chatMembers = users.stream()
                .map(user -> {
                    boolean exists = chatMemberRepository.existsByChatAndMember(chat, user);
                    if (exists) {
                        throw new MyConflictException("User is already member of chat");
                    }

                    ChatMember chatMember = new ChatMember();
                    chatMember.setChat(chat);
                    chatMember.setMember(user);
                    return chatMember;
                }).toList();

        chatMemberRepository.saveAll(chatMembers);

        return ApiResponseDto.success(chatMapper.toDto(chat));
    }

    @Override
    public ApiResponseDto<MyPageDto<ChatDto>> chats(ChatsFilterDto chatsFilterDto) {
        final PageRequest pageRequest = PageRequest.of(chatsFilterDto.getPage(), chatsFilterDto.getSize());

        Page<Chat> chatPage;
        String search = chatsFilterDto.getSearch();
        if (StringUtils.hasLength(search)) {
            chatPage = chatRepository.findAllByNameStartsWith(search, pageRequest);
        } else {
            chatPage = chatRepository.findAllByCreatedBy(CurrentUserUtil.getCurrentUser(), pageRequest);
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

    @Override
    public ApiResponseDto<MyPageDto<UserDto>> chat(UUID id, int page, int size) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Chat not found"));


        Page<ChatMember> chatMemberPage = chatMemberRepository.findAllByChat(chat, PageRequest.of(page, size));

        MyPageDto<UserDto> customPageDto = pageMapper.toCustomPageDto(chatMemberPage);

        List<User> members = chatMemberPage.getContent().stream().map(ChatMember::getMember).toList();
        List<UserDto> memerDtoList = userMapper.toDtoList(members);
        customPageDto.setContent(memerDtoList);

        return ApiResponseDto.success(customPageDto);
    }

    @Override
    public ApiResponseDto<ChatDto> getPrivateChatWith(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new MyNotFoundException("User not found"));

        UUID id = chatMemberRepository.findPrivateChat(user.getId(), CurrentUserUtil.getCurrentUser().getId());

        if (id == null) {
            throw new MyNotFoundException("Chat not found");
        }

        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Chat not found"));

        return ApiResponseDto.success(chatMapper.toDto(chat));
    }
}
