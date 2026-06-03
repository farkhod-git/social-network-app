package com.sn.socialnetworkapp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String firstname;

    String lastname;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    boolean active = false;

    @ManyToOne
    Attachment avatar;

    @CreationTimestamp
    LocalDateTime createdAt;

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
