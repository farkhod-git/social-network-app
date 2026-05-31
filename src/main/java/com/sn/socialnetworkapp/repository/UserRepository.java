package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActiveIsTrue(String email);

    boolean existsByEmailAndActiveIsTrue(String email);
}
