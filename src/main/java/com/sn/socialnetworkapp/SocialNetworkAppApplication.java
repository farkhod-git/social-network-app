package com.sn.socialnetworkapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocialNetworkAppApplication {

    static void main(String[] args) {
        SpringApplication.run(SocialNetworkAppApplication.class, args);
    }

}
