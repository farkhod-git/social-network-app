package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.User;

import java.util.Optional;

public interface UserRepository extends GeneralJpaRepository<User> {
    Optional<User> findByEmail(String email);
}
