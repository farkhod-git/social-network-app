package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query(nativeQuery = true,
    value = """
            select exists(select *
                          from chat ch
                                   inner join chat_members chm on ch.id = chm.chat_id
                          where ch.type = 'PRIVATE'
                            and ch.created_by_id = :from
                            and chm.members_id = :to)""")
    boolean existsPrivateChat(UUID from, UUID to);

    Page<Chat> findAllByCreatedBy(User createdBy, Pageable pageable);

    Page<Chat> findAllByNameStartsWith(String name, Pageable pageable);

    boolean existsByIdAndCreatedBy(UUID id, User createdBy);

    Optional<Chat> findByIdAndCreatedBy(UUID id, User createdBy);

    Optional<Chat> findByIdAndCreatedByAndType(UUID id, User createdBy, ChatTypeEnum type);

    Optional<Chat> findByIdAndMembersContains(UUID id, User member);
}
