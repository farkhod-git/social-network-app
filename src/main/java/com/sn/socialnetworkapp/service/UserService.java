package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.user.ProfileDto;
import com.sn.socialnetworkapp.payload.user.UpdateUserDto;
import com.sn.socialnetworkapp.payload.user.UserDto;

import java.util.UUID;

public interface UserService {
    ApiResponseDto<ProfileDto> profile();

    ApiResponseDto<UserDto> updateUser(UpdateUserDto updateUserDto);

    ApiResponseDto<UserDto> updateAvatar(UUID avatarId);

    void deleteAvatar();
}
