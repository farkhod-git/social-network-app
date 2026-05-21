package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.auth.LoginDto;
import com.sn.socialnetworkapp.payload.auth.TokenDto;
import com.sn.socialnetworkapp.repository.UserRepository;
import com.sn.socialnetworkapp.security.JWTService;
import com.sn.socialnetworkapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public ApiResponseDto<TokenDto> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );

        User user = (User) authentication.getPrincipal();
        assert user != null;
        String accessToken = jwtService.generateAccessToken(user);

        return ApiResponseDto.success(new TokenDto(accessToken));
    }
}
