package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.Message;
import com.sn.socialnetworkapp.entity.MessageRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageReadRepository extends JpaRepository<MessageRead, UUID> {

    List<MessageRead> findAllByMessage(Message message);
}
