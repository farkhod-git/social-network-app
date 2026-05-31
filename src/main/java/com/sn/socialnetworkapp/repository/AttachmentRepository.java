package com.sn.socialnetworkapp.repository;

import com.sn.socialnetworkapp.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}