package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.Attachment;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.exceptions.MyBadRequestException;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.mapper.PageMapper;
import com.sn.socialnetworkapp.mapper.UserMapper;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.MyPageDto;
import com.sn.socialnetworkapp.payload.user.ProfileDto;
import com.sn.socialnetworkapp.payload.user.UpdateUserDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import com.sn.socialnetworkapp.repository.AttachmentRepository;
import com.sn.socialnetworkapp.repository.ChatMemberRepository;
import com.sn.socialnetworkapp.repository.ChatRepository;
import com.sn.socialnetworkapp.repository.UserRepository;
import com.sn.socialnetworkapp.service.UserService;
import com.sn.socialnetworkapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AttachmentRepository attachmentRepository;
    private final PageMapper pageMapper;
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;

    @Override
    public ApiResponseDto<ProfileDto> profile() {
        User currentUser = CurrentUserUtil.getCurrentUser();
        ProfileDto profileDto = userMapper.toProfileDto(currentUser);
        return ApiResponseDto.success(profileDto);
    }

    @Override
    public ApiResponseDto<UserDto> updateUser(UpdateUserDto updateUserDto) {
        User user = CurrentUserUtil.getCurrentUser();
        user.setFirstname(updateUserDto.firstname());
        user.setLastname(updateUserDto.lastname());
        userRepository.save(user);

        return ApiResponseDto.success(userMapper.toDto(user));
    }

    @Override
    public ApiResponseDto<UserDto> updateAvatar(UUID avatarId) {
        Attachment attachment = attachmentRepository.findById(avatarId)
                .orElseThrow(() -> new MyNotFoundException("Attachment not found"));

        if (!attachment.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
            throw new MyBadRequestException("content type is not supported for avatar");
        }

        User currentUser = CurrentUserUtil.getCurrentUser();
        currentUser.setAvatar(attachment);
        userRepository.save(currentUser);

        return ApiResponseDto.success(userMapper.toDto(currentUser));
    }

    @Override
    public void deleteAvatar() {
        User currentUser = CurrentUserUtil.getCurrentUser();

        if (currentUser.getAvatar() == null) {
            throw new MyBadRequestException("Avatar is null");
        }

        currentUser.setAvatar(null);
        userRepository.save(currentUser);
    }

    @Override
    public ApiResponseDto<MyPageDto<UserDto>> users(String search, int page, int size) {
        User user = userRepository.findByEmail(search).orElse(null);
        if (user == null) {
            return ApiResponseDto.success(MyPageDto.empty());
        }

        MyPageDto<UserDto> customPageDto = new  MyPageDto<>();
        customPageDto.setPage(0);
        customPageDto.setSize(1);
        customPageDto.setContent(userMapper.toDtoList(Collections.singletonList(user)));

        return ApiResponseDto.success(customPageDto);
    }
}
