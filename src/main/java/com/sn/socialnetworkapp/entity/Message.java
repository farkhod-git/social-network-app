package com.sn.socialnetworkapp.entity;

import com.sn.socialnetworkapp.entity.abs.AbsGeneralEntity;
import com.sn.socialnetworkapp.enums.ChatTypeEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat extends AbsGeneralEntity {
    @Column(nullable = false)
    String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    Attachment avatar;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ChatTypeEnum type;
}
