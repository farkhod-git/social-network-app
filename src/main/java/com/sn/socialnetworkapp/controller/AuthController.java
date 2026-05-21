package com.sn.socialnetworkapp.controller;

import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.auth.LoginDto;
import com.sn.socialnetworkapp.payload.auth.TokenDto;
import com.sn.socialnetworkapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponseDto<TokenDto> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    // GET /oauth2/authorization/google - starting oauth2 process
    // http://localhost:8080/login/oauth2/code/google -

    // Oauth2
    // 1. Client -> Server by GET /oauth2/authorization/google
    // 2. Server -> Google by https://accounts.google.com/o/oauth2/v2/auth?client_id=...&scope=email profile&redirect_uri=http://localhost:8080/login/oauth2/code/google

}
