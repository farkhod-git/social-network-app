package com.sn.socialnetworkapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String originalName;

    @Column(nullable = false)
    String filename;

    @Column(nullable = false)
    String path;

    @Column(nullable = false)
    String contentType;

    long size;

    @CreationTimestamp
    LocalDateTime createdAt;
}