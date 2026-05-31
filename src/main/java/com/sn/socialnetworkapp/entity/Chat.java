package com.sn.socialnetworkapp.entity;

import com.sn.socialnetworkapp.enums.ChatTypeEnum;
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
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    Attachment avatar;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ChatTypeEnum type;

    @ManyToMany
    List<User> members;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    User createdBy;

    @CreationTimestamp
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;
}
