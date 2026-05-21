package com.sn.socialnetworkapp.entity.abs;

import com.sn.socialnetworkapp.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AbsOwnableEntity extends AbsUpdatableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    User updatedBy;

}
