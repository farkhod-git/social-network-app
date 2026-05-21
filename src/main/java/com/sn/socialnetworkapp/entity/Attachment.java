package com.sn.socialnetworkapp.entity;

import com.sn.socialnetworkapp.entity.abs.AbsGeneralEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment extends AbsGeneralEntity {
    @Column(nullable = false)
    String originalName;

    @Column(nullable = false)
    String path;

    @Column(nullable = false)
    String contentType;

    long size;
}