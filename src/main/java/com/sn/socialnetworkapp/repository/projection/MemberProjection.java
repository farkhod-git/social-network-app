package com.sn.socialnetworkapp.repository.projection;

import java.util.UUID;

public interface MemberProjection {
    UUID getId();

    String getFirstname();

    String getLastname();

    String getEmail();

    UUID getAvatarId();
}
