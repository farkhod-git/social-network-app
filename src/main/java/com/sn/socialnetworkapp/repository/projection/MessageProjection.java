package com.sn.socialnetworkapp.repository.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface MessageProjection {
    Long getId();
    String getContent();
    LocalDateTime getCreatedAt();

    UUID getMediaId();
    String getMediaContentType();
    Long getMediaSize();

    String getUserFirstname();

    String getReplyMessageContent();
    String getReplyMessageUserFirstname();
}
