package com.sn.socialnetworkapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Chat chat;

    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    Message replyMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    Attachment media;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    User createdBy;

    @CreationTimestamp
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;
}
