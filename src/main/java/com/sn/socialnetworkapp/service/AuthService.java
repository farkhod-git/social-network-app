package com.sn.socialnetworkapp.service;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.auth.ResetPasswordConfirmDto;
import com.sn.socialnetworkapp.payload.auth.*;

public interface AuthService {
    ApiResponseDto<TokenDto> login(LoginDto loginDto);

    ApiResponseDto<String> register(RegisterDto registerDto);

    ApiResponseDto<TokenDto> verifyEmail(VerifyEmailDto verifyEmailDto);

    ApiResponseDto<String> resetPassword(ResetPasswordDto resetPasswordDto);

    ApiResponseDto<TokenDto> resetPasswordConfirm(ResetPasswordConfirmDto resetPasswordConfirmDto);

    ApiResponseDto<TokenDto> refreshToken(TokenDto tokenDto);
}
