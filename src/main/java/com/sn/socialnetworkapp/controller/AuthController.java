package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.auth.ResetPasswordConfirmDto;
import com.sn.socialnetworkapp.payload.auth.*;
import com.sn.socialnetworkapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponseDto<TokenDto> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    public ApiResponseDto<String> register(@Valid @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PatchMapping("/verify-email")
    public ApiResponseDto<TokenDto> verifyEmail(@Valid @RequestBody VerifyEmailDto verifyEmailDto) {
        return authService.verifyEmail(verifyEmailDto);
    }

    @PostMapping("/reset-password")
    public ApiResponseDto<String> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        return authService.resetPassword(resetPasswordDto);
    }

    @PatchMapping("/reset-password/confirm")
    public ApiResponseDto<TokenDto>  resetPasswordConfirm(@Valid @RequestBody ResetPasswordConfirmDto resetPasswordConfirmDto) {
        return authService.resetPasswordConfirm(resetPasswordConfirmDto);
    }

    @PutMapping("/refresh-token")
    public ApiResponseDto<TokenDto> refreshToken(@Valid @RequestBody TokenDto tokenDto) {
        return authService.refreshToken(tokenDto);
    }


    // login or register
    // GET /oauth2/authorization/google - starting oauth2 process
    // http://localhost:8080/login/oauth2/code/google

    // Oauth2
    // 1. Client -> Server by GET /oauth2/authorization/google
    // 2. Server -> Google by https://accounts.google.com/o/oauth2/v2/auth?client_id=...&scope=email profile&redirect_uri=http://localhost:8080/login/oauth2/code/google

}
