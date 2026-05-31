package com.sn.socialnetworkapp.service.impl;

import com.sn.socialnetworkapp.service.EmailConfirmCodeService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailConfirmCodeServiceImpl implements EmailConfirmCodeService {

    private final StringRedisTemplate redisTemplate;

    @Value("${email.code-validity}")
    private Integer codeValiditySeconds;

    @Override
    public String generateCode(String email) {
        char c1 = (char) (Math.random() * 10 + 48);
        char c2 = (char) (Math.random() * 10 + 48);
        char c3 = (char) (Math.random() * 10 + 48);
        char c4 = (char) (Math.random() * 10 + 48);

        String code = new String(new char[]{c1, c2, c3, c4});

        redisTemplate.opsForValue().set(
                "email:verify:" + email,
                code,
                Duration.ofSeconds(codeValiditySeconds));

        return code;
    }

    @Nullable
    @Override
    public String getCode(String email) {
        return redisTemplate.opsForValue().get("email:verify:" + email);
    }

    @Override
    public void clearCode(String email) {
        redisTemplate.delete("email:verify:" + email);
    }
}
