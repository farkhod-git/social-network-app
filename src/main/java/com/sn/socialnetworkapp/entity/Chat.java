package com.sn.socialnetworkapp.entity;

import com.sn.socialnetworkapp.entity.abs.AbsOwnableEntity;
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
public class Chat extends AbsOwnableEntity {
    @Column(nullable = false)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    Attachment avatar;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ChatTypeEnum type;
}
