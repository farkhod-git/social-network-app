package com.sn.socialnetworkapp.components;

import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        if (ddlAuto.equals("create")) {
            User superAdmin = new User();
            superAdmin.setActive(true);
            superAdmin.setFirstname("Ali");
            superAdmin.setLastname("Farkhod");
            superAdmin.setEmail("farkhod@gmail.com");
            superAdmin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(superAdmin);
        }
    }
}
