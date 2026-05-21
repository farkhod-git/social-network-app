package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.auth.LoginDto;
import com.sn.socialnetworkapp.payload.auth.TokenDto;

public interface AuthService {
    ApiResponseDto<TokenDto> login(LoginDto loginDto);
}
