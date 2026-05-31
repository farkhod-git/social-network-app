package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.exceptions.MyBadRequestException;
import com.sn.socialnetworkapp.exceptions.MyNotFoundException;
import com.sn.socialnetworkapp.payload.ApiResponseDto;
import com.sn.socialnetworkapp.payload.auth.ResetPasswordConfirmDto;
import com.sn.socialnetworkapp.payload.auth.*;
import com.sn.socialnetworkapp.repository.UserRepository;
import com.sn.socialnetworkapp.security.JWTService;
import com.sn.socialnetworkapp.service.AuthService;
import com.sn.socialnetworkapp.service.EmailConfirmCodeService;
import com.sn.socialnetworkapp.service.EmailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailConfirmCodeService emailConfirmCodeService;
    private final EmailSendingService emailSendingService;

    @Override
    public ApiResponseDto<TokenDto> login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        User user = (User) Objects.requireNonNull(authenticate.getPrincipal());

        return ApiResponseDto.success(getTokenDto(user));
    }

    @Override
    public ApiResponseDto<String> register(RegisterDto registerDto) {
        String password = registerDto.password();
        if (!password.equals(registerDto.confirmPassword())) {
            throw new MyBadRequestException("Passwords are not equals");
        }

        final String email = registerDto.email();
        if (emailConfirmCodeService.getCode(email) != null) {
            throw new MyBadRequestException("Try after a few minutes");
        }

        User user;

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.isActive()) {
                throw new MyBadRequestException("Email already exists");
            }
        } else {
            user = new User();
            user.setEmail(email);
        }

        user.setFirstname(registerDto.firstname());
        user.setLastname(registerDto.lastname());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        String code = emailConfirmCodeService.generateCode(email);

        emailSendingService.send(email, "Confirmation code", "code inputStream " + code);

        return ApiResponseDto.success("Code successfully sent");
    }

    @Override
    public ApiResponseDto<TokenDto> verifyEmail(VerifyEmailDto verifyEmailDto) {
        final String email = verifyEmailDto.email();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new MyBadRequestException("Email not found"));

        if (user.isActive()) {
            throw new MyBadRequestException("Email already exists");
        }

        if (!Objects.equals(emailConfirmCodeService.getCode(email), verifyEmailDto.code())) {
            throw new MyBadRequestException("Code inputStream wrong!");
        }

        emailConfirmCodeService.clearCode(email);
        user.setActive(true);
        userRepository.save(user);

        return ApiResponseDto.success(getTokenDto(user));
    }

    @Override
    public ApiResponseDto<String> resetPassword(ResetPasswordDto resetPasswordDto) {
        String email = resetPasswordDto.email();

        if (emailConfirmCodeService.getCode(email) != null) {
            throw new MyBadRequestException("Try after a few minutes");
        }

        boolean exists = userRepository.existsByEmailAndActiveIsTrue(email);
        if (!exists) {
            throw new MyBadRequestException("Email not found");
        }

        String code = emailConfirmCodeService.generateCode(email);

        emailSendingService.send(email, "Reset password code", "code inputStream " + code);

        return ApiResponseDto.success("Code successfully sent");
    }

    @Override
    public ApiResponseDto<TokenDto> resetPasswordConfirm(ResetPasswordConfirmDto resetPasswordConfirmDto) {
        String password = resetPasswordConfirmDto.password();
        if (!password.equals(resetPasswordConfirmDto.confirmPassword())) {
            throw new MyBadRequestException("Passwords are not equals");
        }

        String email = resetPasswordConfirmDto.email();

        String code = emailConfirmCodeService.getCode(email);
        if (code == null) {
            throw new MyBadRequestException("Code inputStream expired");
        }

        if (!Objects.equals(code, resetPasswordConfirmDto.code())) {
            throw new MyBadRequestException("Code inputStream wrong!");
        }

        emailConfirmCodeService.clearCode(email);

        User user = userRepository.findByEmailAndActiveIsTrue(email)
                .orElseThrow(() -> new MyBadRequestException("Email not found"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return ApiResponseDto.success(getTokenDto(user));
    }

    @Override
    public ApiResponseDto<TokenDto> refreshToken(TokenDto tokenDto) {
        try {
            jwtService.getEmailFromAccessToken(tokenDto.getAccessToken());
            throw new MyBadRequestException("Token inputStream not expired yet");
        } catch (Exception _) {
        }

        String email = jwtService.getEmailFromRefreshToken(tokenDto.getRefreshToken());

        User user = userRepository.findByEmailAndActiveIsTrue(email)
                .orElseThrow(() -> new MyNotFoundException("Email not found"));

        return ApiResponseDto.success(getTokenDto(user));
    }

    private TokenDto getTokenDto(User user) {
        return new TokenDto(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }
}
