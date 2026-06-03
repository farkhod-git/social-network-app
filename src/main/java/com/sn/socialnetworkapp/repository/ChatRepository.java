package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import com.sn.socialnetworkapp.repository.projection.MemberProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Page<Chat> findAllByNameStartsWith(String name, Pageable pageable);

    boolean existsByIdAndCreatedBy(UUID id, User createdBy);

    Optional<Chat> findByIdAndCreatedBy(UUID id, User createdBy);

    Optional<Chat> findByIdAndCreatedByAndType(UUID id, User createdBy, ChatTypeEnum type);

    Page<Chat> findAllByCreatedBy(User createdBy, Pageable pageable);
}
