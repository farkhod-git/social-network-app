package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.Chat;
import com.sn.socialnetworkapp.entity.ChatMember;
import com.sn.socialnetworkapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ChatMemberRepository extends JpaRepository<ChatMember, UUID> {
    boolean existsByChatAndMember(Chat chat, User member);

    Page<ChatMember> findAllByChat(Chat chat, Pageable pageable);

    @Query(nativeQuery = true, value = """
            select chm.chat_id
            from chat_member chm
            inner join users u on u.id = chm.member_id
            where chm.member_id in (:userId1, :userId2)""")
    UUID findPrivateChat(UUID userId1, UUID userId2);
}
