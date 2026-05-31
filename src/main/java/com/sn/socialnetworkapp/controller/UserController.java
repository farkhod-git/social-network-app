package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.user.ProfileDto;
import com.sn.socialnetworkapp.payload.user.UpdateUserDto;
import com.sn.socialnetworkapp.payload.user.UserDto;
import com.sn.socialnetworkapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ApiResponseDto<ProfileDto> profile() {
        return userService.profile();
    }

    @PutMapping
    public ApiResponseDto<UserDto> updateProfile(@Valid @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    @PatchMapping("/avatar/{avatarId}")
    public ApiResponseDto<UserDto> updateAvatar(@PathVariable UUID avatarId) {
        return userService.updateAvatar(avatarId);
    }

    @DeleteMapping("/avatar")
    public void deleteAvatar() {
        userService.deleteAvatar();
    }

}
