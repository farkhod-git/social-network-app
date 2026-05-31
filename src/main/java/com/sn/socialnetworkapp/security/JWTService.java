package com.sn.socialnetworkapp.security;

import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.payload.auth.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {

    @Value("${jwt.access-token.secret-key}")
    private String accessTokenSecretKey;

    @Value("${jwt.access-token.expiry}")
    private long accessTokenExpiry;

    @Value("${jwt.refresh-token.secret-key}")
    private String refreshTokenSecretKey;

    @Value("${jwt.refresh-token.expiry}")
    private long refreshTokenExpiry;

    private SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
    }

    private SecretKey getRefreshTokenSecretKey() {
        return Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes());
    }

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiry, getAccessTokenSecretKey());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiry, getRefreshTokenSecretKey());
    }

    private String generateToken(User user, long expiry, SecretKey secretKey) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiry * 1000))
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromAccessToken(String token) {
        return getSubjectFromToken(token, getAccessTokenSecretKey());
    }

    public String getEmailFromRefreshToken(String token) {
        return getSubjectFromToken(token, getRefreshTokenSecretKey());
    }

    private String getSubjectFromToken(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
