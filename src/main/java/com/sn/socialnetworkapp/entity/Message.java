package com.sn.socialnetworkapp.entity;

import com.sn.socialnetworkapp.entity.abs.AbsOwnableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message extends AbsOwnableEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Chat chat;

    @Column(nullable = false)
    String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Message replyMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    Attachment media;
}
