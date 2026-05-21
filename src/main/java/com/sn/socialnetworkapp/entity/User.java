package com.sn.socialnetworkapp.entity;

import com.sn.socialnetworkapp.entity.abs.AbsGeneralEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbsGeneralEntity implements UserDetails {

    @Column(nullable = false)
    String firstname;

    String lastname;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    Attachment avatar;

    @LastModifiedDate
    LocalDateTime updatedAt;

    // no role and permissions now
    @Transient
    private final Collection<GrantedAuthority> authorities = Collections.emptyList();

    @Override
    public @NonNull String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
