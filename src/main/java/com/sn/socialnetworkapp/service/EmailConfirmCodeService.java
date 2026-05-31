package com.sn.socialnetworkapp.service;

public interface EmailConfirmCodeService {
    String generateCode(String email);

    String getCode(String email);

    void clearCode(String email);
}
