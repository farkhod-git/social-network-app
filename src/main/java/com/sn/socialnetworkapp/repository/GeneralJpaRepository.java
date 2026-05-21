package com.sn.socialnetworkapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface GeneralJpaRepository<T> extends JpaRepository<T, UUID> {
}
