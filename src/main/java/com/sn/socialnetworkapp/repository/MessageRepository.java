package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.Message;
import com.sn.socialnetworkapp.entity.User;
import com.sn.socialnetworkapp.repository.projection.MessageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // DOWN
    @Query(nativeQuery = true, value = """
            select m.id                   as id,
                   m.content              as content,
                   m.created_at           as createdAt,
            
                   a.id                   as mediaId,
                   a.content_type         as mediaContentType,
                   a.size                 as mediaSize,
            
                   u.firstname            as userFirstname,
            
                   substr(rm.content, 10) as replyMessageContent,
                   rmu.firstname          as replyMessageUserFirstname
            from message m
                     inner join users u on m.created_by_id = u.id
                     left join message rm on m.reply_message_id = rm.id
                     left join users rmu on rm.created_by_id = rmu.id
                     left join attachment a on m.media_id = a.id
            
            where m.chat_id = :chatId
              and m.id > :messageId
            order by m.id
            limit :limit""")
    List<MessageProjection> scrollDown(UUID chatId, Long messageId, Integer limit);

    // UP (then reverse)

    @Query(nativeQuery = true, value = """
            select t.*
            from (select m.id                   as id,
                         m.content              as content,
                         m.created_at           as createdAt,
            
                         a.id                   as mediaId,
                         a.content_type         as mediaContentType,
                         a.size                 as mediaSize,
            
                         u.firstname            as userFirstname,
            
                         substr(rm.content, 10) as replyMessageContent,
                         rmu.firstname          as replyMessageUserFirstname
                  from message m
                           inner join users u on m.created_by_id = u.id
                           left join message rm on m.reply_message_id = rm.id
                           left join users rmu on rm.created_by_id = rmu.id
                           left join attachment a on m.media_id = a.id
            
                  where m.chat_id = :chatId
                    and m.id < :messageId
                  order by m.id desc
                  limit :limit) t
            order by t.id""")
    List<MessageProjection> scrollUp(UUID chatId, Long messageId, Integer limit);

    Optional<Message> findByIdAndCreatedBy(Long messageId, User createdBy);
}
