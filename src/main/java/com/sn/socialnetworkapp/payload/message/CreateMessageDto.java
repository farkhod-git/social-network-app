package com.sn.socialnetworkapp.payload.message;

import java.util.UUID;

public record CreateMessageDto(String content,
                               Long replyMessageId,
                               UUID mediaId) {
}
